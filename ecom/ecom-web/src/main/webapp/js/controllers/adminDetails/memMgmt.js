var angular = require('angular');

var memMgmtController = function($scope, memberService, sellerService) {

	$scope.error = false;
	$scope.incomplete = false;
	$scope.sellerSelected = false;
	$scope.edit = false;
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

	$scope.showUsers = function () {
		memberService.GetAll().then(function(res){
			$scope.users = res;
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
					user.memberID = res.memberID;
				});	


			}else{

				sellerService.Create(user).then(function(res) {
					user.memberID = res.memberID;
				});
			}


			$scope.users.push(user);		
		}
		else{

			delete password;

			if($scope.data.singleSelect == 'M'){
				delete user.sellerInfo;
				memberService.Update(user).then(function(res) {

					user = res;

				});
			}else{
				sellerService.Update(user).then(function(res) {

					user = res;

				});
			}



			$scope.users[$scope.indexMemberList] = user;
			$scope.edit = false;
		}

		emptyFields();

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
			memberService.Delete($scope.users[index].memberID);
		}else if($scope.users[index].accountType == 'S'){
			sellerService.Delete($scope.users[index].memberID);
		}
		$scope.users.splice(index, 1);
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
	}


};

module.exports = memMgmtController;
