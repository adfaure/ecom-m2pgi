var angular = require('angular');

var memMgmtController = function($scope,  publicPhoto, orderService, memberService, sellerService) {

	$scope.error = false;
	$scope.incomplete = false;
	
	console.log("MgmtController");

	memberService.GetAll().then(function(res){
		$scope.users = res;
	});

	$scope.edit = true;

	$scope.save = function(){
		console.log("Edit..");
		console.log("The edit is: "+$scope.edit);
		
		if($scope.edit == true){
			var user = {
					firstName: $scope.firstName,
					lastName: $scope.lastName, 
					email: $scope.email,
					login: $scope.login,
					password: $scope.passw1,
					accountType: 'M'
				
			};
			console.log("The user: "+
					user.firstName + " " +
					user.lastName + " " +
					user.email + " " +
					user.login + " " +
					user.password + " " +
					user.accountType + " ");
			
			var res = memberService.Create(user);
			
			console.log("El resultado es: "+res);
			
		}
	}
	
	$scope.editUser = function(id) {
		emptyFields();
		if (id == 'new') {
			$scope.edit = true;
			//$scope.incomplete = true;
		} else {
			$scope.edit = false;
			$scope.memberID = $scope.users[id-1].memberID;
			$scope.accountType = $scope.users[id-1].accountType;
			$scope.firstName = $scope.users[id-1].firstName;
			$scope.lastName = $scope.users[id-1].lastName; 
			$scope.email = $scope.users[id-1].email;
			$scope.login = $scope.users[id-1].login;
		}
	};

	function emptyFields(){
		$scope.memberID = '';
		$scope.accountType = '';
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.login = '';
		$scope.passw1 = '';
		$scope.passw2 = '';
	}
	
	/*$scope.submit = function () {
		console.log("The edit bla bla");
	};*/

};

module.exports = memMgmtController;
