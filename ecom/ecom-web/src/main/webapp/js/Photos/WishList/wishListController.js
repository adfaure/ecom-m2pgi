var angular = require('angular');

var wishes = function($scope, $location, $filter, alertService, apiToken, publicPhoto) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", "you need to be logged in to access to this zone");
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
