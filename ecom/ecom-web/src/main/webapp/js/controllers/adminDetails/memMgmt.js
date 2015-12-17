var angular = require('angular');

var memMgmtController = function($scope, $sce, $filter, memberService, sellerService, alertService) {

	$scope.error = false;
	$scope.incomplete = false;
	$scope.sellerSelected = false;
	$scope.edit = false;
	$scope.existingLogin = false;
	$scope.existingEmail = false;
	$scope.indexMemberList = 0;

	$scope.data = {
			singleSelect: null
	};

	$scope.inEditMember = {
			memberID:"",
			email: "",
			firstName: "",
			lastName: "",
			accountType: 'M',
			login : "",
			password : "",
			sellerInfo : {
				rib : ""
			}
	};

	$scope.users = [];
	var cachedUsers = [];

	$scope.showUsers = function () {
		memberService.GetAll().then(function(res){
			$scope.users = cachedUsers = res.filter( function(member) {
				return member.accountType != 'A';
			})
		});
	};


	$scope.showUsers();

	$scope.reset = function(){
		if($scope.data.singleSelect == 'S'){
			$scope.sellerSelected = true;
			$scope.inEditMember.accountType = 'S';
		}
		else{
			$scope.sellerSelected = false;
			$scope.inEditMember.accountType = 'M';
			$scope.inEditMember.sellerInfo.rib = "";
		}
	};

	//$scope.$watch('inEditMember.email',function() {$scope.test();});
	//$scope.$watch('inEditMember.login',function() {$scope.test();});
	//$scope.$watch('inEditMember.password', function() {$scope.test();});

	/*$scope.test = function() {


		$scope.incomplete = false;
		if ((!$scope.edit && (!$scope.inEditMember.email.length ||
				!$scope.inEditMember.login.length ||
				!$scope.inEditMember.password.length)) ||

				($scope.edit && (!$scope.inEditMember.email.length ||
				!$scope.inEditMember.login.length))) {

			$scope.creationForm.loginInput.$setValidity("incomplete fields", false);
			$scope.incomplete = true;
		}
		else{
			$scope.creationForm.loginInput.$setValidity("incomplete fields", true);
		}
	};*/

	$scope.checkEmail = function () {

		$scope.creationForm.email.$setValidity("creation email", true);
		$scope.existingEmail = false;
		if($scope.edit && ($scope.inEditMember.email != $scope.users[$scope.indexMemberList].email) || (!$scope.edit)){
			if ($scope.inEditMember.email) {
	            memberService.IsExistingByEmail($scope.inEditMember.email).then(function (res) {
	                if (res) {
	                    $scope.existingEmail = true;
	                    $scope.creationForm.email.$setValidity("creation email", false);
	                } else {
	                    $scope.existingEmail = false;
	                    $scope.creationForm.email.$setValidity("creation email", true);
	                }
	                return $scope.existingEmail;
	            });
	        }
		}
    }

	$scope.checkLogin = function() {

		$scope.creationForm.loginInput.$setValidity("inscription login", true);
		$scope.existingLogin = false;
		if($scope.edit && ($scope.inEditMember.login != $scope.users[$scope.indexMemberList].login) || (!$scope.edit) ){
			memberService.IsExisting($scope.inEditMember.login).then(
					function(res) {
						if(res) {

							$scope.existingLogin = true;
							$scope.creationForm.loginInput.$setValidity("inscription login", false);
						} else { //login not found"

							$scope.existingLogin = false;
							$scope.creationForm.loginInput.$setValidity("inscription login", true);
						}
					}
				);
		}

	}


	$scope.save = function(){
		var user = {
				memberID : $scope.inEditMember.memberID,
				email: $scope.inEditMember.email,
				firstName: $scope.inEditMember.firstName,
				lastName: $scope.inEditMember.lastName,
				login : $scope.inEditMember.login,
				password : $scope.inEditMember.password,
				accountType: $scope.inEditMember.accountType,
				sellerInfo : {

					rib : $scope.inEditMember.sellerInfo.rib
				}
		};

		if(!$scope.edit){
			var res;

			if($scope.data.singleSelect == 'M'){

				delete user.sellerInfo;

				memberService.Create(user).then(function(res) {
					//user.memberID = res.memberID;
					showCreatedUser(res);
				});


			}else{

				sellerService.Create(user).then(function(res) {
					//user.memberID = res.memberID;
					showCreatedUser(res);
				});
			}

		}
		else{

			delete password;

			if($scope.data.singleSelect == 'M'){
				delete user.sellerInfo;
				memberService.Update(user).then(function(res) {
					addEditedUser(res, $scope.indexMemberList);

				});
			}else{
				sellerService.Update(user).then(function(res) {
					addEditedUser(res, $scope.indexMemberList);

				});
			}
		}



	}

	$scope.editUser = function(index) {
		emptyFields();
		if (index == 'new') {
			$scope.edit = false;
		} else {
			$scope.edit = true;
			$scope.inEditMember.accountType = $scope.users[index].accountType;
			$scope.inEditMember.memberID = $scope.users[index].memberID;
			$scope.inEditMember.firstName = $scope.users[index].firstName;
			$scope.inEditMember.lastName = $scope.users[index].lastName;
			$scope.inEditMember.email = $scope.users[index].email;
			$scope.inEditMember.login = $scope.users[index].login;
			$scope.inEditMember.password = $scope.users[index].password;
			$scope.data.singleSelect = $scope.users[index].accountType;
			$scope.reset();
			if($scope.inEditMember.sellerInfo != null && $scope.users[index].sellerInfo != null)
				$scope.inEditMember.sellerInfo.rib = $scope.users[index].sellerInfo.rib;

			$scope.indexMemberList = index;
		}
	};


	$scope.deleteUser = function(index) {
		if($scope.users[index].accountType == 'M'){
			memberService.Delete($scope.users[index].memberID).then(function(res) {
				takeOutUser(res, index);
			});
		}else if($scope.users[index].accountType == 'S'){
			sellerService.Delete($scope.users[index].memberID).then(function(res) {
				takeOutUser(res, index);
			});
		}
	};


	function emptyFields(){


		$scope.inEditMember.memberID = "";
		//$scope.inEditMember.accountType = "M";
		$scope.inEditMember.firstName = "";
		$scope.inEditMember.lastName = "";
		$scope.inEditMember.email = "";
		$scope.inEditMember.login = "";
		$scope.inEditMember.password = "";
		$scope.inEditMember.sellerInfo.rib = "";

		$scope.creationForm.$setPristine();
	}

	function showCreatedUser(res){

		if(res.success != null && !res.success){
			alertService.add("alert-danger", $sce.trustAsHtml(" Erreur, le membre n'as pas pu été ajouté", 2000));
		}
		else{
			res.active = true;
			$scope.users.push(res);
			emptyFields();
			//$scope.creationForm.$setPristine();
		}
	}


	function addEditedUser(res, index){
		if(res.success != null && res.success == false){
			alertService.add("alert-danger", $sce.trustAsHtml("Erreur, le membre n'as pas pu etre édité", 2000));
		}
		else{
			res.active=true;
			$scope.users[index] = res;
			$scope.edit = false;
			emptyFields();
			//$scope.creationForm.$setPristine();
		}
	}

	function takeOutUser(res, index){
		if(res.success != null && res.success == false){
			alertService.add("alert-danger", $sce.trustAsHtml(" Erreur, le membre n'as pas pu etre supprimé", 2000));
		}
		else{
			//$scope.users.splice(index, 1);
			$scope.users[index].active=false;
		}
	}


};

module.exports = memMgmtController;
