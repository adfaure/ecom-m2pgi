var angular = require('angular');
var ecomApp = require('./../app');

var headerController = function($scope,$location, apiToken, authentificationService) {
    $scope.auth = false;
    $scope.$watch( apiToken.isAuthentificated, function(isAuth) {
            $scope.auth = isAuth;
            if($scope.auth) {
                $scope.user = apiToken.getUser();
                var userWatch = $scope.$watch(apiToken.getUser, function(user) {
                    $scope.user = user;
                });
            } else {
                if(userWatch) userWatch();
            }
        }
    );

    $scope.logout = authentificationService.logout;

    $scope.gotToProfil = function(subview) {
        $location.path('/profil').search( {
            'section' : subview
        });
    };

    $scope.goToSearch = function() {
        $location.path('/search').search( {
            'terms' : $scope.terms
        });
    };
};


ecomApp.controller('headerController', headerController);
module.exports = headerController;