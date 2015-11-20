var angular = require('angular');

var administratorController = function($scope,  publicPhoto, orderService, memberService, sellerService) {
	
	publicPhoto.GetPhotoCount().then(function(res) {
            console.log(res);
            $scope.countText = res;
            $scope.photoCount = res;
    });
	
	
	orderService.GetOrderCount().then(function(res) {
        console.log(res);
        $scope.purchaseCount = res;
	});
	
	orderService.GetTotalPurchaseCost().then(function(res) {
        console.log(res);
        $scope.totalPurchaseCost = res;
	});
	
	memberService.GetCount().then(function(res) {
        console.log(res);
        $scope.memberCount = res;
	});
	
	sellerService.GetCount().then(function(res) {
        console.log(res);
        $scope.sellerCount = res;
	});
	
};


module.exports = administratorController;
