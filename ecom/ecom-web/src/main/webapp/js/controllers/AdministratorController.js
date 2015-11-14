var angular = require('angular');

var administratorController = function($scope,  publicPhoto, orderService, memberService, sellerService) {
	
	publicPhoto.GetPhotoCount().then(function(res) {
            $scope.countText = res;
            $scope.photoCount = res;
    });
	
	
	orderService.GetOrderCount().then(function(res) {
        $scope.purchaseCount = res;
	});
	
	orderService.GetTotalPurchaseCost().then(function(res) {
        $scope.totalPurchaseCost = res;
	});
	
	memberService.GetCount().then(function(res) {
        $scope.memberCount = res;
	});	
	
	sellerService.GetCount().then(function(res) {
        $scope.sellerCount = res;
	});
	
};


module.exports = administratorController;
