var angular = require('angular');

var accueilController = function($scope, $location, apiToken, publicPhoto) {
  var cachedPhotos = [];
  $scope.photos = [];
  $scope.connected = false;
  $scope.welcome = true;

  $scope.search = {
    searching : false,
    terms : ''
  };

  var user;

  if(apiToken.isAuthentificated()) {
    user = apiToken.getUser();
    $scope.connected = true;
  }

  publicPhoto.GetAll().then(function(res) {
    $scope.photos = cachedPhotos = res;
  });

  $scope.register = function() {
    $location.path("/inscription");
  }

  $scope.hideWelcome = function() {
    $scope.welcome = false;
  }

  $scope.$on('elastic', function(event, data) {
    $scope.search.terms = data.query;
    if (!data.query) {
      $scope.photos = cachedPhotos;
      $scope.search.searching = false;
    }
    else {
      $scope.search.searching = true;
      publicPhoto.Search($scope.search.terms).then(function(res) {
        $scope.photos = res;
        $scope.search.searching = false;
      });
    }

  });
};

module.exports = accueilController;
