var angular = require('angular');

var wishes = function($scope, $location, alertService, apiToken, publicPhoto) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", "you need to be logged in to access to this zone");
        $location.path("/");
    }

    var user = apiToken.getUser();

    publicPhoto.GetUserWishedPhotosById(user.memberID).then(function(res) {
        $scope.photos = res;
        console.log(res);
    });

    $scope.unwish = function(index) {
      console.log("unwishing");
      publicPhoto.RemovePhotoFromWishList($scope.photos[index].photoId, user.memberID).then(function(res) {
        console.log($scope.photos);
        $scope.photos.splice(index, 1);
        console.log($scope.photos);
      });
    };
};

module.exports = wishes;
