var angular = require('angular');
var ecomApp = require('./../app');

var headerController = function($rootScope, $scope, $location, apiToken, authentificationService, searchService) {
    $scope.auth = apiToken.isAuthentificated();

    $scope.$watch(apiToken.isAuthentificated, function(isAuth) {
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

    $scope.placeholder = "Rechercher dans le site";

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

    $scope.terms = '';

    $scope.search = function() {
      $rootScope.$broadcast('search', {query: $scope.terms});
    }
};

ecomApp.controller('headerController', headerController);
module.exports = headerController;
