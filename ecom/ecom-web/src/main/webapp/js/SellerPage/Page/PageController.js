var angular = require('angular');


var controller = function($scope, $location, alertService, $routeParams, memberService, pageService, publicPhoto, apiToken) {

    $scope.photos = [];
    
    $scope.followed = false;
    $scope.logged = false;
    $scope.sameSeller = false;
    
    if(!$scope.user) { // si le scope parent ne contient pas déjà
        publicPhoto.GetUserPhotosWithId($routeParams.id).then(
            function (res) {
                $scope.photos = res;
            }
        );
        pageService.getPage($routeParams.id).then(function (res) {
            $scope.page = res.data;
        });
    }
    
    var userIDFollower = 0;
    var followedID = $routeParams.id;
	if(apiToken.isAuthentificated()) {
		userIDFollower  = apiToken.getUser().memberID;
		$scope.logged = true;
		if(userIDFollower == followedID){
			$scope.sameSeller = true;
		}else{
			memberService.IsFollowedBy(followedID, userIDFollower).then(function(res){
		    	var isFollowed = res;
		    	$scope.followed = isFollowed;
		    });
		}
    } 
	
    
    $scope.follow = function(){
    	memberService.follow(userIDFollower, followedID).then(function(res){
    		if(res){
    			$scope.followed = true;
    		}
    	});
    };
    
    
    $scope.unfollow = function(){
    	memberService.unfollow(userIDFollower, followedID).then(function(res){
    		if(res){
    			$scope.followed = false;
    		}
    	});
    };
    
    $scope.details = function(photoId) {
        if(isNaN(photoId)) return;
        if($scope.photos) {
            var photo = $scope.photos.find(function(photo) {
                return (photo.photoID == photoId);
            });
        }
        if(photo)
            $location.path('/photos/details/' + photoId).search( {
                'photo' :JSON.stringify(photo)
            });
    };

};

module.exports = controller;
