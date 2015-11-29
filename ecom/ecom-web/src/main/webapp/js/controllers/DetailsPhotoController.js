var angular = require('angular');

var detailsPhotoController = function($scope, $routeParams, apiToken, publicPhoto) {
    if($routeParams.photo) {
        $scope.photo = JSON.parse($routeParams.photo);
    }

    var user;

    if(apiToken.isAuthentificated()) {
        user = apiToken.getUser();
    }


    $scope.wish = function (photoID){
        if(apiToken.isAuthentificated()) 
            publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function(res) {
            });
        else
            console.log("TODO : redirect to authentification");
    }


    $scope.like = function (photoID){
        if(apiToken.isAuthentificated()) 
            publicPhoto.AddPhotoToLikeList(photoID, user.memberID).then(function(res) {
            });
        else
            console.log("TODO : redirect to authentification");
    }
};

module.exports = detailsPhotoController;
