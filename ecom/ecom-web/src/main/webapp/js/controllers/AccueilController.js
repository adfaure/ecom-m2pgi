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

};

module.exports = accueilController;