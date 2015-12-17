var angular = require('angular');


var upgrade = function($scope, $location, $sce, $routeParams,apiToken, alertService,sellerService) {
    $scope.RIB    = "";
    $scope.submit = submit;

    var user   = apiToken.getUser();

    if($scope.user.accountType == 'S') {
        $scope.subview = 'details';
    }

    function submit() {
        if($scope.RIB != "") {
            user.sellerInfo = {};
            user.sellerInfo.rib = $scope.RIB;
            sellerService.CreateFromMember(user).then(function(data) {
                if(data.success) {
                    apiToken.setUser(data.user);
                    apiToken.setToken(data.token);
                    if($routeParams.redirect) {
                        alertService.add("alert-success", $sce.trustAsHtml("<strong>Vous pouvez à présent commencer à uplodaer des photo !</strong>"), 2000);
                        $location.path($routeParams.redirect);
                    } else {
                        $scope.setView('details');
                    }
                }
            });
        }
    }
};

module.exports = upgrade;
