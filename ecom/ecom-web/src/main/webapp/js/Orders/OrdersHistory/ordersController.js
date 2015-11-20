var angular = require('angular');

var orders = function($scope,$location,alertService ,apiToken, orderService) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", "you need to be logged to access to this zone");
        $location.path("/");
    }

    var user = apiToken.getUser();

    orderService.GetOrdersWithSellerLogin(user.login).then(function(res) {
        $scope.orders = res;
    });

};

module.exports = orders;
