var angular = require('angular');

var accueilController = function($scope, $location, apiToken, publicPhoto) {
    var cachedPhotos = [];
    $scope.hovering = false;


    var user;

    if(apiToken.isAuthentificated()) {
        user = apiToken.getUser();
    }


    publicPhoto.GetAll().then(function(res) {
            $scope.photos = cachedPhotos = res;
        }
    );

    

/*
    $scope.search = {
        terms : '',
        hitCount : 0,
        took : 0
    };

    $scope.elasticsearch = function (){
      publicPhoto.Search($scope.terms).then(function(res) {
        $scope.search.terms = $scope.terms;
        $scope.search.hitCount = res.totalHits;
        $scope.search.took = res.took;
        $scope.photos = res.hits;
      });
    };

    $scope.sortByDate = function (){
      publicPhoto.GetAllSortByDate().then(function(res) {
        $scope.photos = res;
      });
    };

    $scope.sortByPrice = function (){
      publicPhoto.GetAllSortByPrice().then(function(res) {
        $scope.photos = res;
      });
    };

    $scope.sortByViews = function (){
      publicPhoto.GetAllSortByViews().then(function(res) {
        $scope.photos = res;
      });
    };

    $scope.sortByLikes = function (){
      publicPhoto.GetAllSortByLikes().then(function(res) {
        $scope.photos = res;
      });
    };

    $scope.photosFromCache = function (){
      $scope.terms = '';
      $scope.search.hitCount = null;
      $scope.photos = cachedPhotos;
    }
*/
};

module.exports = accueilController;
