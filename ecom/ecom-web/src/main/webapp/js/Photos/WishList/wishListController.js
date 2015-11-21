var angular = require('angular');

var wishes = function($scope, $location, alertService, apiToken, publicPhoto) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", "you need to be logged in to access to this zone");
        $location.path("/");
    }

    var user = apiToken.getUser();

    $scope.photos = {};

    publicPhoto.GetUserWishedPhotosById(user.memberID).then(function(res) {
        $scope.photos = res;
    });

    $scope.unwish = function(index) {
      publicPhoto.RemovePhotoFromWishList($scope.photos[index].photoId, user.memberID).then(function(res) {
        $scope.photos.splice(index, 1);
      });
    };
};

module.exports = wishes;
