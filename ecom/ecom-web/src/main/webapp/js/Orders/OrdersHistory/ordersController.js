var angular = require('angular');

var orders = function($scope, $location, $sce, alertService ,apiToken, orderService) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour effectuer cette action ...</strong>"), 3000);
        $location.path("/");
    }

    var user = apiToken.getUser();

    orderService.GetOrdersWithSellerLogin(user.login).then(function(res) {
        if(res.success)
            $scope.orders = res.data;
        else {
            $scope.orders = [];
        }
    });

};

module.exports = orders;
