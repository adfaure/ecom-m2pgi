var angular = require('angular');
var ecomApp = require('./../app');

var navsidebarController = function($scope, $location, $routeParams, apiToken) {
  $scope.auth = false;
  $scope.seller = false;
  $scope.admin = false;
  $scope.url = "";

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

    $scope.$on('$routeChangeSuccess', function(event){
        $scope.url = $location.url();
            //params = $location.search();
        console.log(event+" : "+$scope.url);
    })

    $scope.isActiveClass = function(checkedUrl) {
        if($scope.url == checkedUrl)
            return "activeTab";
        else
            return "";
    };
};

ecomApp.controller('navsidebarController', navsidebarController);
module.exports = navsidebarController;
