var angular = require('angular');

var thumbnailElementDirective = function ($compile, $location, $sce, apiToken, publicPhoto, alertService) {
    return {
        restrict: 'E',
        scope : {
            photo : "=photo"
        },
        templateUrl: './js/Photos/Thumbnail/photoThumbnailTemplate.html',
        controller : function ($scope) {

            $scope.isAdmin = false;
            $scope.mine = false;

            $scope.$watch(apiToken.isAuthentificated, function(isAuth) {
                    if(isAuth) {
                        $scope.$watch(apiToken.getUser, function(user) {
                            $scope.isAdmin  = (user && user.accountType == "A");
                            $scope.mine = (user && user.accountType == "S" && user.memberID == $scope.photo.sellerID);
                        });
                    }
                }
            );

            $scope.details = function (photoId) {
                $location.path('/photos/details/' + photoId);
            };

            $scope.wish = function (photoID){
                if(apiToken.isAuthentificated()) {
                    var user = apiToken.getUser();
                    publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function (res) {
                        $scope.photo.wishlisted = true;
                    });
                } else {
                  alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour effectuer cette action ...</strong>"), 3000);
                }
            };

            /* En attente du commit sur les photos*/
            $scope.unwish  = function (photoID){
                if(apiToken.isAuthentificated()) {
                    var user = apiToken.getUser();
                    publicPhoto.RemovePhotoFromWishList(photoID, user.memberID).then(function (res) {
                        $scope.photo.wishlisted = false;
                    });
                }
            };

            $scope.like = function(photoID){
                if(apiToken.isAuthentificated()) {
                    var user = apiToken.getUser();
                    publicPhoto.AddPhotoToLikeList(photoID, user.memberID).then(function (res) {
                        $scope.photo.liked = true;
                        $scope.photo.likes++;
                    });
                } else {
                  alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour effectuer cette action ...</strong>"), 3000);
                }
            };

            $scope.unlike = function(photoID){
                if(apiToken.isAuthentificated()) {
                    var user = apiToken.getUser();
                    publicPhoto.RemovePhotoFromLikeList(photoID, user.memberID).then(function (res) {
                        $scope.photo.liked = false;
                        $scope.photo.likes--;
                    });
                }
            }

            $scope.flag = function (photoID){
                if(apiToken.isAuthentificated()) {
                    var user = apiToken.getUser();
                    console.log($scope.photo.sellerID);
                    console.log(user.id);
                    if(!$scope.photo.flagged)  {
                      publicPhoto.Flag(photoID, user.memberID).then(function(res) {
                          $scope.photo.flagged = true;
                      });
                    }
                } else {
                  alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour effectuer cette action ...</strong>"), 3000);
                }
            };
        }
    };

};

module.exports = thumbnailElementDirective;
