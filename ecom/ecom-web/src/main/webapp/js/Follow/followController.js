var angular = require('angular');


var followController = function($scope, $location, apiToken, sellerService, memberService, publicPhoto) {
	
	$scope.numberPhotos = 3;
	
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
	
	$scope.goToSellerPage = function(index){
  	  $location.path("/seller/page/"+$scope.followed[index].sellerID);
    }
	
};

module.exports = followController;