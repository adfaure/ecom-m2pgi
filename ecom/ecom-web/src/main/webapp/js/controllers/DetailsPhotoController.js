var angular = require('angular');

var detailsPhotoController = function($scope, $routeParams, apiToken, publicPhoto) {
    /*if($routeParams.photo) {
        $scope.photo = JSON.parse($routeParams.photo);
    }*/
    $scope.photo = publicPhoto.GetById().then(function(res) {
      $scope.photo = res;
      console.log($scope.photo);
    });

    var user = apiToken.getUser();

    $scope.wish = function (photoID) {
      if (user != null) {
        publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function(res) {});
      } else {

      }
    }
};

module.exports = detailsPhotoController;
