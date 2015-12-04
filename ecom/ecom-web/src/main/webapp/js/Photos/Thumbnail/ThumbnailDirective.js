var angular = require('angular');

var thumbnailElementDirective = function ($compile, $location) {
    return {
        restrict: 'E',
        scope : {
            photo : "=photo"
        },
        templateUrl: './js/Photos/Thumbnail/photoThumbnailTemplate.html',
        controller : function ($scope ) {
            console.log("yaaaa");
            $scope.details = details;
            $scope.wish    = wish;
            $scope.unwish  = unwish;
            $scope.like    = like;
            $scope.unlike  = unlike;
        }
    };

    function details (photoId) {
        $location.path('/photos/details/' + photoId);
    }

     function wish(photoID){
        if(apiToken.isAuthentificated())
            publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function(res) {
                if(photo)
                    photo.wishlisted=true;
            });
    }

    /* En attente du commit sur les photos*/
    function unwish(photoID){
        if(apiToken.isAuthentificated())
            publicPhoto.RemovePhotoFromWishList(photoID, user.memberID).then(function(res) {
                if(photo)
                    photo.wishlisted=false;
            });
    }

    function like(photoID){
        if(apiToken.isAuthentificated())
            publicPhoto.AddPhotoToLikeList(photoID, user.memberID).then(function(res) {
                if(photo)
                    photo.liked=true;
            });
    }

     function unlike(photoID){
        if(apiToken.isAuthentificated())
            publicPhoto.RemovePhotoFromLikeList(photoID, user.memberID).then(function(res) {
                if(photo)
                    photo.liked=false;
            });
    }

};

module.exports = thumbnailElementDirective;