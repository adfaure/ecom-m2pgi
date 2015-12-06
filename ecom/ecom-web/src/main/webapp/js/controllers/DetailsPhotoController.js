var angular = require('angular');

var detailsPhotoController = function($scope, $location, $routeParams, apiToken, publicPhoto) {
    var photoID = $routeParams.id;
    if(photoID) {

      publicPhoto.GetById(photoID).then(function(res) {
        $scope.photo = res;
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
      
      $scope.like = function (photoID){
          if(apiToken.isAuthentificated()) 
              publicPhoto.AddPhotoToLikeList(photoID, user.memberID).then(function(res) {
              });
          else
              console.log("TODO : redirect to authentification");
      }
      
      $scope.goToSellerPage = function(){
    	  
    	  $location.path("/seller/page/"+$scope.photo.sellerID);
    	  console.log("vino a user page");    	  
      }
      
    }// ICI on doit afficher un message pour dire que la photo n'éxiste pas.

};

module.exports = detailsPhotoController;
