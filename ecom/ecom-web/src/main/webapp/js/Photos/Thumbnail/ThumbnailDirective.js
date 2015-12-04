var angular = require('angular');

var thumbnailElementDirective = function ($compile, $location, apiToken, publicPhoto) {
    return {
        restrict: 'E',
        scope : {
            photo : "=photo"
        },
        templateUrl: './js/Photos/Thumbnail/photoThumbnailTemplate.html',
        controller : function ($scope ) {
            $scope.photo;
            var user = $scope.user = apiToken.getUser();
            if(photo.wishes) {
                
            }

            $scope.details = function (photoId) {
                $location.path('/photos/details/' + photoId);
            };

            $scope.wish = function (photoID){
                if(apiToken.isAuthentificated())
                    publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function(res) {
                        $scope.photo.wishlisted=true;
                    });
            };

            /* En attente du commit sur les photos*/
            $scope.unwish  = function (photoID){
                if(apiToken.isAuthentificated())
                    publicPhoto.RemovePhotoFromWishList(photoID, user.memberID).then(function(res) {
                        $scope.photo.wishlisted=false;
                    });
            };

            $scope.like = function(photoID){
                if(apiToken.isAuthentificated())
                    publicPhoto.AddPhotoToLikeList(photoID, user.memberID).then(function(res) {
                        $scope.photo.liked = true;
                        $scope.photo.likes++;
                    });
            };

            $scope.unlike = function(photoID){
                if(apiToken.isAuthentificated())
                    publicPhoto.RemovePhotoFromLikeList(photoID, user.memberID).then(function(res) {
                        $scope.photo.liked=false;
                        $scope.photo.likes--;
                    });
            }
        }
    };

};

module.exports = thumbnailElementDirective;