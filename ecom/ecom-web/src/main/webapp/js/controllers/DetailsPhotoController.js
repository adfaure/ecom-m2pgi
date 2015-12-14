var angular = require('angular');

var detailsPhotoController = function($scope, $location, $routeParams, apiToken, publicPhoto, sellerService) {

  var photoID = $routeParams.id;
  if(isNaN(photoID)) return;// ICI on doit afficher un message pour dire que la photo n'éxiste pas.

  var seller = {};
  $scope.photo = {};
  $scope.loaded = false;
  publicPhoto.GetById(photoID).then(function(res) {
    $scope.photo = res;
    $scope.loaded = true;
    return sellerService.GetById(res.sellerID);
  }).then(function(seller) {
      $scope.seller = seller;
  });

  var user;

  if(apiToken.isAuthentificated()) {
    user = apiToken.getUser();
  }

  $scope.wish = function (photoID) {
    if (user != null) {
      publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function(res) {
        $scope.photo.wishlisted = true;
      });
    } else {
      $location.path('/login');
    }
  };

  $scope.unwish = function (photoID) {
    publicPhoto.RemovePhotoFromWishList(photoID, user.memberID).then(function(res) {
      $scope.photo.wishlisted = false;
    });
  };

  $scope.flag = function (photoID) {
    publicPhoto.Flag(photoID, user.memberID).then(function(res) {
      $scope.photo.flagged = true;
    });
  };

  $scope.like = function (photoID){
    if(apiToken.isAuthentificated())
    publicPhoto.AddPhotoToLikeList(photoID, user.memberID).then(function(res) {
    });
  };

  $scope.goToSellerPage = function(){
    $location.path("/seller/page/"+ $scope.photo.sellerID);
  }
};

module.exports = detailsPhotoController;
