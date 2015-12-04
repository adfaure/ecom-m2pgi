var angular = require('angular');

var reportedController = function($scope, publicPhoto) {

	$scope.photos = [];

	publicPhoto.GetReportedPhotos().then(function(res) {
       $scope.photos = res;
  });

	$scope.delete = function(index) {
		publicPhoto.DeletePhotoById($scope.photos[index].photoID).then(function(res) {
			$scope.photos.splice(index, 1);
		});
	};

	$scope.validate = function(index) {
		/*publicPhoto.ValidatePhotoById($scope.photos[index].photoID).then(function(res) {
			$scope.photos.splice(index, 1);
		});*/
	};
};

module.exports = reportedController;
