var angular = require('angular');

var MemDetailsController = function($scope, $location, $routeParams, apiToken, publicPhoto, memberService, sellerService) {
	//$routeParams.id;

	var userID;
	var memb;
	$scope.edit = false;
	
	
	if(apiToken.isAuthentificated()) {
		  userID = apiToken.getUser().memberID;
		  if(apiToken.getUser().accountType == 'M'){
			  $scope.AccountType = "Membre";
		  }else{
			  $scope.AccountType = "Vendeur";
		  }
		  //CREATE NEW SERVICE so the password won't be needed. 
		  memberService.GetById(userID).then(function(res){ memb = res;});
	}
	
	//$location.path('/login');

	/*$scope.flag = function (photoID) {
          publicPhoto.Flag(photoID, user.memberID).then(function(res) {
            $scope.photo.flagged = true;
          });
      }*/

	$scope.cancel = function(){
		$scope.user = memb;
		
		$scope.edit = false;
	}
	
	$scope.save = function(){

		if($scope.user.accountType == 'M'){
			delete memb.sellerInfo;
			
			memberService.Update(memb).then(function(res) {
				user = res;
			});
			
		}else{
			sellerService.Update(memb).then(function(res) {
				user = res;
			});
		}	
		
		
		$scope.edit = false;
	}

};

module.exports = MemDetailsController;
