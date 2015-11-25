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

	$scope.updateListUsers = function () {
		memberService.GetAll().then(function(res){
			$scope.users = res;
		});
	};

	$scope.updateListUsers();

	$scope.reset = function(){
		console.log("It came after clicking in the combobox");
		console.log($scope.data.singleSelect);
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
		console.log("Edit..");
		console.log("The edit is: "+$scope.edit);

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
			console.log("The user: "+
					$scope.user.firstName + " " +
					$scope.user.lastName + " " +
					$scope.user.email + " " +
					$scope.user.login + " " +
					$scope.user.password + " " +
					$scope.user.accountType + " ");

			var res;

			if($scope.data.singleSelect == 'M'){
				res = memberService.Create(user);
			}else{

				res = sellerService.Create(user);
			}

			$scope.users.push(user);		
		}
		else{

			var res = memberService.Update(user);

			$scope.users[$scope.indexMemberList] = user;

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
		$scope.inEditMember.accountType = "M";
		$scope.inEditMember.firstName = "";
		$scope.inEditMember.lastName = "";
		$scope.inEditMember.email = "";
		$scope.inEditMember.login = "";
		$scope.inEditMember.password = "";
		$scope.inEditMember.sellerInfo.rib = "";
	}


};

module.exports = memMgmtController;
