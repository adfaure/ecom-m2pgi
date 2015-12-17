var angular = require('angular');

var MemDetailsController = function($scope, $location, $routeParams, $sce, apiToken, publicPhoto, memberService, sellerService, alertService) {
	//$routeParams.id;

	var userID;
	var memb = null;
	$scope.edit = false;
	$scope.addRIB = false;
	$scope.editPSW = false;
	//$scope.equalsPSW = false;
	$scope.compteVendeur = false;
	$scope.pswTheSame = true;
	$scope.existingEmail = false;
	

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


		//REMARK: This is done to get the password because IF I don't send the password, then it's set to NULL in the database when updating the member
		memberService.GetById(userID).then(function(res){ memb = res;  fillOutInfo();});
	}


	function fillOutInfo(){
		$scope.userDataForm.$setPristine();
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
		
		$scope.userPSWForm.$setPristine();
		
		$scope.user1.pswActuel = "";
		$scope.user1.pswNouveau = "";
		$scope.user1.pswConfirmation = "";
	}

	$scope.cancel = function(){
		if($scope.editPSW){
			emptyPSWvalues();
			$scope.editPSW = false;
		}else if($scope.addRIB){
			$scope.userRIBForm.$setPristine();
			$scope.user1.sellerInfo.rib = "";
			$scope.addRIB = false;
		}else{
			fillOutInfo();
			$scope.existingEmail = false;
			$scope.edit = false;
		}

	}

	//$scope.$watch('user1.pswActuel',function() {$scope.test();});
	$scope.$watch('user1.pswNouveau',function() {$scope.test();});
	$scope.$watch('user1.pswConfirmation',function() {$scope.test();});
	
	//$scope.$watch('user1.email',function() {$scope.test();});
	//$scope.$watch('user1.sellerInfo.rib',function() {$scope.test();});

	$scope.test = function() {
		//$scope.equalsPSW = false;
		//$scope.actPSWfilled = false;
		
		$scope.pswTheSame = true;
		
		if($scope.editPSW && ($scope.user1.pswNouveau && $scope.user1.pswConfirmation)){
		
			//If the passwords are not the same or if one of them is empty (and therefore the other as well)
			if(($scope.user1.pswNouveau.replace(/\s+/g, '') !== '' && $scope.user1.pswConfirmation.replace(/\s+/g, '') !== '') 
					&& ($scope.user1.pswNouveau === $scope.user1.pswConfirmation)){
				$scope.userPSWForm.pswConfirmation.$setValidity("the passwords don't match", true);
				$scope.pswTheSame = true;
			}else{
				$scope.userPSWForm.pswConfirmation.$setValidity("the passwords don't match", false);
				$scope.pswTheSame = false;
			}
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
					alertService.add("alert-danger", $sce.trustAsHtml("<strong>Erreur, le mot de passe actuel n'est pas correct !</strong>"), 3000);
					$scope.user1.password = prevPSW;
				}
				else{
					updateMemSeller(res);
					$scope.editPSW = false;
					//emptyPSWvalues();S
					alertService.add("alert-success", $sce.trustAsHtml("<strong>Mot de passe modifié! </strong>"), 2000);
				}
			});


		}
		else if($scope.addRIB){

			sellerService.CreateFromMember($scope.user1).then(function(res){

				if(res.success){
					alertService.add("alert-success", $sce.trustAsHtml("<strong>Votre compte a bien été upgradé </strong>"), 2000);
					//if it's not successful then say it couldn't be.

					$scope.AccountType = "Vendeur";
					$scope.addRIB = false;
					$scope.compteVendeur = true;

					apiToken.setUser(res.user);
					apiToken.setToken(res.token);
					fillOutInfo();


				}else{
					alertService.add("alert-danger", $sce.trustAsHtml("<strong>Erreur lors de l'upgrade de votre compte</strong>"), 3000);
				}
			});

		}else{

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
		
		if(res.success != null && !res.success){
			alertService.add("alert-danger", " Erreur, la compte n'as pas pu etre édité", 2000);
		}
		else{
			if(res.user != null){
				//because when upgrading a member, the res.user is returned.
				memb = res.user;
			}
			else{
				memb = res;
			}
		}
		
		fillOutInfo();
		
	}
	
    $scope.checkEmail = function () {

    	$scope.userDataForm.emailAddress.$setValidity("update email", true);
    	$scope.existingEmail = false;
    	
        if ($scope.user1.email && ($scope.user1.email != memb.email)) {
            memberService.IsExistingByEmail($scope.user1.email).then(function (res) {
                if (res) {
                    $scope.existingEmail = true;
                    $scope.userDataForm.emailAddress.$setValidity("update email", false);
                } else {
                    $scope.existingEmail = false;
                    $scope.userDataForm.emailAddress.$setValidity("update email", true);
                }
                return $scope.existingEmail;
            });
        }
    }



};

module.exports = MemDetailsController;
