var angular = require('angular');

var orders = function($scope,$location,alertService ,apiToken, orderService) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", "you need to be logged to access to this zone");
        $location.path("/");
    }

    var user = apiToken.getUser();

    orderService.GetOrdersWithSellerLogin(user.login).then(function(res) {
        console.log(res);
        if(res.success)
            $scope.orders = res.data;
        else {
            $scope.orders = [];
        }
    });

};

module.exports = orders;
