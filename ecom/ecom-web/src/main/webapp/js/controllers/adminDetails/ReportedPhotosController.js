var angular = require('angular');

var reportedController = function($scope, $location, $filter, publicPhoto) {

	var cachedPhotos = [];
	$scope.photos = [];
	$scope.query = '';

	publicPhoto.GetReportedPhotos().then(function(res) {
       $scope.photos = cachedPhotos = res;
  });

	$scope.delete = function(index) {
		publicPhoto.DeleteReportedPhoto($scope.photos[index].photoID).then(function(res) {
			$scope.photos.splice(index, 1);
		});
	};

	$scope.validate = function(index) {
		publicPhoto.ValidateReportedPhoto($scope.photos[index].photoID).then(function(res) {
			$scope.photos.splice(index, 1);
		});
	};

	$scope.goto = function(id) {
		$location.path('/photos/details/' + id)
	};

	$scope.$on('search', function(event, data) {
		$scope.query = data.query;
		if (!data.query) $scope.photos = cachedPhotos;
		$scope.photos = $filter('matchQueries')(cachedPhotos, data.query);
	});
};

module.exports = reportedController;
