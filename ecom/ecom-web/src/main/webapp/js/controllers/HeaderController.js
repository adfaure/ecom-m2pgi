var angular = require('angular');
var ecomApp = require('./../app');

var headerController = function($rootScope, $scope, $location, $sce, apiToken, authentificationService, alertService, searchService) {
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

    $scope.placeholder = "Rechercher dans le site...";

    $scope.logout = authentificationService.logout;

    $scope.gotToProfil = function(subview) {
        $location.path('/profil').search( {
            'section' : subview
        });
    };

    $scope.goToAddPhoto = function() {
        if($scope.auth)
            $location.path('/profil/addPhoto');
        else
            alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour uploader une photo ...</strong>"), 3000);
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
