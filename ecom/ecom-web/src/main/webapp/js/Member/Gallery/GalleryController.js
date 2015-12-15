var angular = require('angular');

var gallery = function($scope, $location, $filter, apiToken, publicPhoto) {
    var user;
    var photo = [];
    if(!apiToken.isAuthentificated()) {
        $location.path('/');
    } else {
        user = apiToken.getUser();
    }

    var cachedPhotos = [];
    $scope.photos = [];
    $scope.query = '';

    publicPhoto.getBoughtPhoto(user.memberID).then(function(res) {
            $scope.photos = cachedPhotos = res;
        }
    );

    $scope.$on('search', function(event, data) {
  		$scope.query = data.query;
  		if (!data.query) $scope.photos = cachedPhotos;
  		$scope.photos = $filter('matchQueries')(cachedPhotos, data.query);
  	});
};

module.exports = gallery;
