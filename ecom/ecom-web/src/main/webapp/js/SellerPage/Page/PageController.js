var angular = require('angular');


var controller = function($scope, $location, alertService, $routeParams, memberService, pageService, publicPhoto) {

    $scope.photos = [];

    if(!$scope.user) { // si le scope parent ne contient pas déjà
        publicPhoto.GetUserPhotosWithId($routeParams.id).then(
            function (res) {
                $scope.photos = res;
            }
        );
        pageService.getPage($routeParams.id).then(function (res) {
            $scope.page = res.data;
        });
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

};

module.exports = controller;
