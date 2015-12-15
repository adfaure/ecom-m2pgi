var angular = require('angular');

var accueilController = function($scope, $location, apiToken, publicPhoto) {
  var cachedPhotos = [];
  $scope.photos = [];

  var user;

  if(apiToken.isAuthentificated()) {
    user = apiToken.getUser();
  }

  publicPhoto.GetAll().then(function(res) {
    $scope.photos = cachedPhotos = res;
  });
};

module.exports = accueilController;
