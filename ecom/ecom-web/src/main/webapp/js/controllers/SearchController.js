var angular = require('angular');

var searchController = function($scope, $routeParams, apiToken, $location, publicPhoto) {
    var cachedPhotos = [];
    var searchTerms = "";


    var user;

    if(apiToken.isAuthentificated()) {
        user = apiToken.getUser();
    }


    console.log($routeParams);


    publicPhoto.GetAll().then(function(res) {
            $scope.photos = cachedPhotos = res;
        }
    );


    if($routeParams.terms) {
    	searchTerms = $routeParams.terms;
    }

    
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


    $scope.search = {
            terms : '',
            hitCount : 0,
            took : 0
    };
    
    $scope.photosFromCache = function (){
        $scope.terms = '';
        $scope.search.hitCount = null;
        $scope.photos = cachedPhotos;
    }

    // Starting search
    publicPhoto.Search(searchTerms).then(function(res) {
      $scope.search.terms = searchTerms;
      $scope.search.hitCount = res.totalHits;
      $scope.search.took = res.took;
      $scope.photos = res.hits;
    });


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
                    photo.liked=false;
            });
        else
            console.log("TODO : redirect to authentification");
    }

};

module.exports = searchController;