var angular = require('angular');


var followController = function($scope, $location, apiToken, sellerService, memberService, publicPhoto) {
	
	$scope.numberPhotos = 5;
	
    var userIDFollower = 0;
	if(apiToken.isAuthentificated()) {
		userIDFollower  = apiToken.getUser().memberID;
    } 
	
	
	
	publicPhoto.GetLastPhotosFromSellers(userIDFollower, $scope.numberPhotos).then(function(res){
		$scope.followed = res;
	});
	
	
	/*memberService.GetAllFollowedSellersBy(userIDFollower).then(function(res){
		$scope.users = res;
	});*/
	
	$scope.goToSellerPage = function(){
  	  //$location.path("/seller/page/"+sellerID);
    }
	
};

module.exports = followController;