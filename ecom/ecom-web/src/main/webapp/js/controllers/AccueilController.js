var angular = require('angular');

var accueilController = function($scope, $location, apiToken, publicPhoto) {
  var cachedPhotos = [];
  $scope.photos = [];
  $scope.connected = false;

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
};

module.exports = accueilController;
