var angular = require('angular');

var accueilController = function($scope,$location ,publicPhoto) {

    publicPhoto.GetAll().then(function(res) {
            console.log(res);
            $scope.photos = res;
        }
    );

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
    }

    $scope.hitCount = 0;

    $scope.search = {
        term: '',
    };

    $scope.search = function (){
      publicPhoto.Search($scope.search.term).then(function(res) {
              $scope.hitCount = res.totalHits;
              $scope.took = res.took;
              $scope.photos = res.hits;
              console.log(res);
          }
      );
    }

};

module.exports = accueilController;
