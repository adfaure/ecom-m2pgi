var angular = require('angular');

var accueilController = function($scope, $location, apiToken, publicPhoto) {
    var cachedPhotos = [];
    $scope.hovering = false;


    var user;

    if(apiToken.isAuthentificated()) {
        user = apiToken.getUser();
    }


    publicPhoto.GetAll().then(function(res) {
            $scope.photos = cachedPhotos = res;
            console.log(res);
        }
    );

    $scope.details = function(photoId) {

        if(isNaN(photoId)) return;
        if($scope.photos) {
            var photo = $scope.photos.find(function(photo) {
                return (photo.photoID == photoId);
            });
        }
        if(photo)
            $location.path('/photos/details/' + photoId);
        
    };


    $scope.wish = function (photoID){
        console.log("wishing");
        if(apiToken.isAuthentificated()) 
            publicPhoto.AddPhotoToWishList(photoID, user.memberID).then(function(res) {
                if($scope.photos) {
                    var photo = $scope.photos.find(function(photo) {
                        return (photo.photoID == photoID);
                    });
                }
                if(photo)
                    photo.wishlisted=true;
            });
        else
            console.log("TODO : redirect to authentification");
    }

    /* En attente du commit sur les photos*/
    $scope.unwish = function (photoID){
        console.log("unwishing");
        if(apiToken.isAuthentificated()) 
            publicPhoto.RemovePhotoFromWishList(photoID, user.memberID).then(function(res) {
                if($scope.photos) {
                    var photo = $scope.photos.find(function(photo) {
                        return (photo.photoID == photoID);
                    });
                }
                if(photo)
                    photo.wishlisted=false;
            });
        else
            console.log("TODO : redirect to authentification");
    }
    

    $scope.like = function (photoID){
        console.log("liking");
        if(apiToken.isAuthentificated()) 
            publicPhoto.AddPhotoToLikeList(photoID, user.memberID).then(function(res) {
                if($scope.photos) {
                    var photo = $scope.photos.find(function(photo) {
                        return (photo.photoID == photoID);
                    });
                }
                if(photo)
                    photo.liked=true;
            });
        else
            console.log("TODO : redirect to authentification");
    }

    
    $scope.unlike = function (photoID){
        console.log("unliking");
        if(apiToken.isAuthentificated()) 
            publicPhoto.RemovePhotoFromLikeList(photoID, user.memberID).then(function(res) {
                if($scope.photos) {
                    var photo = $scope.photos.find(function(photo) {
                        return (photo.photoID == photoID);
                    });
                }
                if(photo)
                    photo.liked=false;
            });
        else
            console.log("TODO : redirect to authentification");
    }
    

/*
    $scope.search = {
        terms : '',
        hitCount : 0,
        took : 0
    };

    $scope.elasticsearch = function (){
      publicPhoto.Search($scope.terms).then(function(res) {
        $scope.search.terms = $scope.terms;
        $scope.search.hitCount = res.totalHits;
        $scope.search.took = res.took;
        $scope.photos = res.hits;
      });
    };

    $scope.sortByDate = function (){
      publicPhoto.GetAllSortByDate().then(function(res) {
        $scope.photos = res;
      });
    };

    $scope.sortByPrice = function (){
      publicPhoto.GetAllSortByPrice().then(function(res) {
        $scope.photos = res;
      });
    };

    $scope.sortByViews = function (){
      publicPhoto.GetAllSortByViews().then(function(res) {
        $scope.photos = res;
      });
    };

    $scope.sortByLikes = function (){
      publicPhoto.GetAllSortByLikes().then(function(res) {
        $scope.photos = res;
      });
    };

    $scope.photosFromCache = function (){
      $scope.terms = '';
      $scope.search.hitCount = null;
      $scope.photos = cachedPhotos;
    }
*/
};

module.exports = accueilController;
