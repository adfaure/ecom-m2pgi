var angular = require('angular');

var detailsPhotoController = function($scope, $routeParams, publicPhoto) {
    if($routeParams.photo) {
        $scope.photo = JSON.parse($routeParams.photo);
    }
};

module.exports = detailsPhotoController;