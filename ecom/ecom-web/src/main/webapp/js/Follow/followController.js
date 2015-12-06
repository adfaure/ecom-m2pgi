var angular = require('angular');


var followController = function($scope, $location, apiToken, sellerService, memberService) {
   
    var userIDFollower = 0;
	if(apiToken.isAuthentificated()) {
		userIDFollower  = apiToken.getUser().memberID;
    } 
	
	
	memberService.GetAllFollowedSellersBy(userIDFollower).then(function(res){
		$scope.users = res;
		console.log("The result is: "+res);
	});
	
	$scope.goToSellerPage = function(sellerID){
	  console.log("the seller id is: "+sellerID);
  	  $location.path("/seller/page/"+sellerID);
  	     	  
    }
	
};

module.exports = followController;