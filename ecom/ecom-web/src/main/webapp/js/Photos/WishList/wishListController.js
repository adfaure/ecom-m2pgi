var angular = require('angular');

var wishes = function($scope, $location, $sce, $filter, alertService, apiToken, publicPhoto) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour effectuer cette action ...</strong>"), 3000);
        $location.path("/");
    }

    var user = apiToken.getUser();

    var cachedPhotos = [];
    $scope.photos = {};

    $scope.query = '';

    publicPhoto.GetUserWishedPhotosById(user.memberID).then(function(res) {
      $scope.photos = cachedPhotos = res;
    });

    $scope.unwish = function(index) {
      publicPhoto.RemovePhotoFromWishList($scope.photos[index].photoId, user.memberID).then(function(res) {
        $scope.photos.splice(index, 1);
      });
    };

    $scope.$on('search', function(event, data) {
      $scope.query = data.query;
      if (!data.query) $scope.photos = cachedPhotos;
      $scope.photos = $filter('matchQueries')(cachedPhotos, data.query);
    });
};

module.exports = wishes;
