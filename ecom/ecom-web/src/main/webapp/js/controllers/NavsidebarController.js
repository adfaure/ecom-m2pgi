var angular = require('angular');
var ecomApp = require('./../app');

var navsidebarController = function($scope, $location, $routeParams, apiToken) {
    $scope.auth = false;
    $scope.seller = false;
    $scope.selected = "accueil";

    $scope.$watch( apiToken.isAuthentificated, function(isAuth) {
            $scope.auth = isAuth;
            if($scope.auth) {
                $scope.user = apiToken.getUser();
                var userWatch = $scope.$watch(apiToken.getUser, function(user) {
                    $scope.user = user;
                    $scope.seller = (user.accountType == "S");
                });
            } else {
                if(userWatch) userWatch();
            }
        }
    );
};


ecomApp.controller('navsidebarController', navsidebarController);
module.exports = navsidebarController;