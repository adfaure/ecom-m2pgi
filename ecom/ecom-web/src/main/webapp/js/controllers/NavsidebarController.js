var angular = require('angular');
var ecomApp = require('./../app');

var navsidebarController = function($scope, $location, $routeParams, apiToken) {
  $scope.auth = false;
  $scope.seller = false;
  $scope.admin = false;
  $scope.selected = "accueil";

  $scope.$watch(apiToken.isAuthentificated, function(isAuth) {
            $scope.auth = isAuth;
            if($scope.auth) {
                $scope.user = apiToken.getUser();
                var userWatch = $scope.$watch(apiToken.getUser, function(user) {
                    $scope.user = user;
                    $scope.seller = (user && user.accountType == "S");
                    $scope.admin  = (user && user.accountType == "A");
                });
            } else {
              $scope.auth = false;
              $scope.seller = false;
              $scope.admin = false;
              if(userWatch) userWatch();
            }
        }
    );
};

ecomApp.controller('navsidebarController', navsidebarController);
module.exports = navsidebarController;
