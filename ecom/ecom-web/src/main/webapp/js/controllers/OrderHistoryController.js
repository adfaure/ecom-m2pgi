var angular = require('angular');

var OrderHistoryController = function($scope, $http) {

  $http.get("api/orders").success(function(response) {
    $scope.orders = response;
    $scope.orderCount = response.length;
  });
};

module.exports = OrderHistoryController;
