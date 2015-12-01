var angular = require('angular');

var detailsPhotoController = function($scope, $location, $routeParams, apiToken, publicPhoto) {
    var photoID = $routeParams.id;
    if(photoID) {

      publicPhoto.GetById(photoID).then(function(res) {
        $scope.photo = res;
      });

      var user = apiToken.getUser();

      $scope.wish = function (photoID) {
        if (user != null) {
          publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function(res) {
            $scope.photo.wishlisted = true;
          });
        } else {
          $location.path('/login');
        }
      }

      $scope.unwish = function (photoID) {
          publicPhoto.RemovePhotoFromWishList(photoID, user.memberID).then(function(res) {
            $scope.photo.wishlisted = false;
          });
      }

      $scope.flag = function (photoID) {
          publicPhoto.Flag(photoID, user.memberID).then(function(res) {
            $scope.photo.flagged = true;
          });
      }
    }// ICI on doit afficher un message pour dire que la photo n'éxiste pas.
};

module.exports = detailsPhotoController;
