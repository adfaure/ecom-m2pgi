var angular = require('angular');

var MemDetailsController = function($scope, $location, $routeParams, apiToken, publicPhoto, memberService, sellerService, alertService) {
	//$routeParams.id;

	var userID;
	var memb = null;
	$scope.edit = false;
	$scope.addRIB = false;
	$scope.editPSW = false;
	$scope.equalsPSW = false;
	$scope.compteVendeur = false;
	
	$scope.sellerCheckBox = false;

	$scope.user1 = {
			memberID:"",
			email: "",
			firstName: "",
			lastName: "",
			accountType: '',
			login : "",
			password : "",
			sellerInfo : {
				rib : ""
			}
	};

	
	if(apiToken.isAuthentificated()) {
		userID = apiToken.getUser().memberID;
		if(apiToken.getUser().accountType == 'M'){
			$scope.AccountType = "Membre";
			$scope.possibleUpgrade = true;
		}else{
			$scope.AccountType = "Vendeur";
			$scope.compteVendeur = true;
		}
		
		
		
		//CREATE NEW SERVICE so the password won't be needed. 
		//REMARK: IF I don't send the password, then it's set to NULL in the database when updating the member
		memberService.GetById(userID).then(function(res){ memb = res;  fillOutInfo();});
	}
	

	function fillOutInfo(){
		if(memb != null){
			$scope.user1.memberID = memb.memberID;
			$scope.user1.email = memb.email;
			$scope.user1.firstName = memb.firstName;
			$scope.user1.lastName = memb.lastName;
			$scope.user1.accountType = memb.accountType;
			$scope.user1.login = memb.login;
			$scope.user1.password = memb.password;
			if(memb.sellerInfo != null){
				$scope.user1.sellerInfo.rib = memb.sellerInfo.rib;
			}
		}
	}

	function emptyPSWvalues(){
		$scope.user1.pswActuel = "";
		$scope.user1.pswNouveau = "";
		$scope.user1.pswConfirmation = "";
	}

	$scope.cancel = function(){
		if($scope.editPSW){
			emptyPSWvalues();
			$scope.editPSW = false;
		}else if($scope.addRIB){
			$scope.user1.sellerInfo.rib = "";
			$scope.addRIB = false;
		}else{
			fillOutInfo();
			$scope.edit = false;
		}
		
	}

	$scope.$watch('user1.pswNouveau',function() {$scope.test();});
	$scope.$watch('user1.pswConfirmation',function() {$scope.test();});

	$scope.test = function() {
		$scope.equalsPSW = false;

		//If the passwords are not the same or if one of them is empty (and therefore the other as well)
		if(($scope.user1.pswNouveau === $scope.user1.pswConfirmation) && $scope.user1.pswNouveau !== ''){
			$scope.equalsPSW = true;
		}
		else{
			//mettre un msg d'alerte.
		}
		
		
	};


	$scope.save = function(){
		
		var actPSW = $scope.user1.pswActuel;
		var newPSW = $scope.user1.pswNouveau;

		delete $scope.user1.pswActuel;
		delete $scope.user1.pswNouveau;
		delete $scope.user1.pswConfirmation;
		
		
		if($scope.editPSW){
			
			var prevPSW = $scope.user1.password;
			$scope.user1.password = actPSW;			
			
			memberService.ChangePassword($scope.user1, newPSW).then(function(res){
				if((res.success != null && !res.success) || !res){
					alertService.add("alert-danger", " Erreur, le mot de passe actuel n'est pas correct", 2000);
					$scope.user1.password = prevPSW;
				}
				else{
					console.log(res);
					updateMemSeller(res);
					
					$scope.editPSW = false;
					//emptyPSWvalues();S
					alertService.add("alert-success", "Mot de pass modifié! ", 2000);
				}
			});
			
			
		}
		else if($scope.addRIB){
			
			sellerService.CreateFromMember($scope.user1).then(function(res){
				
				if(res.success){
					alertService.add("alert-success", "Upgrade done! ", 2000);
					//if it's not successful then say it couldn't be.
					
					$scope.AccountType = "Vendeur";
					$scope.addRIB = false;
					$scope.compteVendeur = true;
					
					memberService.GetById(userID).then(function(res){ 
						memb = res;  
						apiToken.setUser(memb);
						fillOutInfo(); 
					});
					
					//updateMemSeller(res);
					
					//changeToken(res);
					
				}else{
					alertService.add("alert-danger", " Erreur, the upgrade couldn't be done", 2000);
				}
				
				

			});
			
			
		}else{
			//A ce moment la, le password est ok, et on va mettre toute la info.
			
			if($scope.user1.accountType == 'M'){
				delete $scope.user1.sellerInfo;

				memberService.Update($scope.user1).then(function(res) {
					updateMemSeller(res);
				});

			}else{
				sellerService.Update($scope.user1).then(function(res) {
					updateMemSeller(res);
					
				});
			}
			
			$scope.edit = false;
		}
	}
	

	function updateMemSeller(res){
		console.log("res es " +res);
		
		if(res.user != null){
			//because when upgrading a member, the res.user is returned.
			memb = res.user;
		}
		else{
			memb = res;
		}
		
		fillOutInfo();
	}
	
	function changeToken(res){
		if(res){
			apiToken.setUser(res);
		}
		
	}
	
};

module.exports = MemDetailsController;
