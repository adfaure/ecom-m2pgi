var angular = require('angular');

var reportedController = function($scope, $location, publicPhoto) {

	$scope.photos = [];

	publicPhoto.GetReportedPhotos().then(function(res) {
       $scope.photos = res;
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
};

module.exports = reportedController;
