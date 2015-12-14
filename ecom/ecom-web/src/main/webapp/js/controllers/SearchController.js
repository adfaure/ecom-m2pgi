var angular = require('angular');

var searchController = function($scope, $location, $routeParams, apiToken, publicPhoto) {
  var cachedPhotos = [];
  var searchTerms = "";

  var user;

  if(apiToken.isAuthentificated()) {
    user = apiToken.getUser();
  }

  publicPhoto.GetAll().then(function(res) {
    $scope.photos = cachedPhotos = res;
  });

  if($routeParams.terms) {
    searchTerms = $routeParams.terms;
  }

  $scope.details = function(photoId) {
    if(isNaN(photoId)) return;
    $location.path('/photos/details/' + photoId);
  };

  $scope.search = {
    terms : '',
    hitCount : 0,
    took : 0
  };

  $scope.photosFromCache = function() {
    $scope.terms = '';
    $scope.search.hitCount = null;
    $scope.photos = cachedPhotos;
  }

  publicPhoto.Search(searchTerms).then(function(res) {
    $scope.search.terms = searchTerms;
    $scope.search.hitCount = res.totalHits;
    $scope.search.took = res.took;
    $scope.photos = res.hits;
  });

  $scope.$on('search', function(event, mass) { console.log(mass); });
};

module.exports = searchController;
