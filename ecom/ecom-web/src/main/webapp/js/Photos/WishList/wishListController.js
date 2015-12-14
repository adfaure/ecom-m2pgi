var angular = require('angular');

var wishes = function($scope, $location, $sce, alertService, apiToken, publicPhoto) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour effectuer cette action ...</strong>"), 3000);
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
