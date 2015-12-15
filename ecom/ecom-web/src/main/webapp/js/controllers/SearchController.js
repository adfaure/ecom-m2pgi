var angular = require('angular');

var searchController = function($scope, $location, $routeParams, apiToken, publicPhoto) {

  $scope.search = {
    terms : '',
    hitCount : 0,
    took : 0
  };

  $scope.photos = [];

  var user;

  if(apiToken.isAuthentificated()) {
    user = apiToken.getUser();
  }

  if($routeParams.terms) {
    $scope.search.terms = $routeParams.terms;
  }

  if(!$scope.search.terms) {
    publicPhoto.GetAll().then(function(res) {
      $scope.photos = res;
    });
  } else {
    publicPhoto.Search($scope.search.terms).then(function(res) {
      $scope.search.hitCount = res.totalHits;
      $scope.search.took = res.took;
      $scope.photos = res.hits;
    });
  }
};

module.exports = searchController;
