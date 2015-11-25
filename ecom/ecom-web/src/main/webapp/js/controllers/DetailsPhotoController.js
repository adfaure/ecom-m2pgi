var angular = require('angular');

var detailsPhotoController = function($scope, $routeParams, apiToken, publicPhoto) {
    if($routeParams.photo) {
        $scope.photo = JSON.parse($routeParams.photo);
    }

    if(!apiToken.isAuthentificated()) {

    }

    var user = apiToken.getUser();

    $scope.wish = function (photoID){
      publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function(res) {

      });
    }
};

module.exports = detailsPhotoController;
