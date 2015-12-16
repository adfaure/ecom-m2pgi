var angular = require('angular');
var ecomApp = require('./../app');

var headerController = function($rootScope, $scope, $location, $sce, apiToken, authentificationService, alertService,cartService ,searchService) {
    $scope.auth = apiToken.isAuthentificated();
    $scope.cart = cartService.getCart();
    $scope.$watch(apiToken.isAuthentificated, function(isAuth) {
            $scope.auth = isAuth;
            $scope.cart = cartService.getCart();
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

    $scope.$watch(cartService.getCart, function(cart) {
        $scope.cart = cart;
    });

    $scope.placeholder = "Rechercher dans le site...";

    $scope.logout = authentificationService.logout;

    $scope.gotToProfil = function(subview) {
        $location.path('/profil').search( {
            'section' : subview
        });
    };

    $scope.goToAddPhoto = function() {
        if($scope.auth) {
            if ($scope.seller)
                $location.path('/profil/addPhoto');
            else
                alertService.add("alert-info", $sce.trustAsHtml("<strong>Il faut posseder un <a href='#/profil/upgrade'>compte vendeur</a> pour uploader un photo</strong>"), 3000);
        }
        else
            alertService.add("alert-info", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour uploader une photo ...</strong>"), 3000);
    };

    $scope.terms = '';

    $scope.search = function() {
      $rootScope.$broadcast('search', {query: $scope.terms});
    }

    $scope.elastic = function() {
      $rootScope.$broadcast('elastic', {query: $scope.terms});
    }

    $scope.change = function() {
      $rootScope.$broadcast('search', {query: $scope.terms});
      if(!$scope.terms) $rootScope.$broadcast('elastic', {query: $scope.terms});
    }
};

ecomApp.controller('headerController', headerController);
module.exports = headerController;
