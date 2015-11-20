var angular = require('angular');

var searchController = function($scope, $routeParams, $location, publicPhoto) {
    var cachedPhotos = [];
    var searchTerms = "";

    console.log($routeParams);

    publicPhoto.GetAll().then(function(res) {
            $scope.photos = cachedPhotos = res;
        }
    );


    if($routeParams.terms) {
    	searchTerms = $routeParams.terms;
    }

    
    $scope.details = function(photoId) {
        if(isNaN(photoId)) return;
        if($scope.photos) {
            var photo = $scope.photos.find(function(photo) {
                return (photo.photoID == photoId);
            });
        }
        if(photo)
            $location.path('/photos/details/' + photoId).search( {
                'photo' :JSON.stringify(photo)
            });
    };

    $scope.search = {
            terms : '',
            hitCount : 0,
            took : 0
    };
    
    $scope.photosFromCache = function (){
        $scope.terms = '';
        $scope.search.hitCount = null;
        $scope.photos = cachedPhotos;
    }

    // Starting search
    publicPhoto.Search(searchTerms).then(function(res) {
      $scope.search.terms = searchTerms;
      $scope.search.hitCount = res.totalHits;
      $scope.search.took = res.took;
      $scope.photos = res.hits;
    });

};

module.exports = searchController;