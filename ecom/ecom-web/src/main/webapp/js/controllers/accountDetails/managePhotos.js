var angular = require('angular');

var manage = function($scope, $location , $routeParams, publicPhoto, apiToken) {

	$scope.form = {
			id : '',
			name : '',
			description : '',
			tags : '',
			price : 0
	};

	$scope.photos = [];
	$scope.highlight = -1;
	
	publicPhoto.GetUserPhotos(apiToken.getUser().login).then(function(res) {
		$scope.photos = res;

		if($routeParams.photo) {
			var paramPhoto = JSON.parse($routeParams.photo);
			var idx = $scope.photos.findIndex(function(photo) {
				return (paramPhoto .photoID == photo.photoID)
			});

			if (idx != -1) {
				$scope.highlight = idx;
			}
		}
	});

	$scope.editIndex = -1;
	$scope.removeIndex = -1;
	$scope.valid = false;
	$scope.processing = false;

	$scope.edit = function(index) {
		$scope.form.id = $scope.photos[index].photoId;
		$scope.form.name = $scope.photos[index].name;
		$scope.form.description = $scope.photos[index].description;
		$scope.form.tags = $scope.photos[index].tags;
		$scope.form.price = $scope.photos[index].price;
		$scope.editIndex = index;
	};


	$scope.save = function(index) {
		$scope.processing = true;

		publicPhoto.Update($scope.form).then(function(res) {
			$scope.photos[index].name = $scope.form.name;
			$scope.photos[index].description = $scope.form.description;
			$scope.photos[index].tags = $scope.form.tags;
			$scope.photos[index].price = $scope.form.price;
			$scope.processing = false;
			$scope.removeIndex = -1;
			$scope.editIndex = -1;
		});
	};

	$scope.cancel = function() {
		$scope.editIndex = -1;
		$scope.processing = false;
	};

	$scope.remove = function(index){
		$scope.processing = true;
		$scope.removeIndex = index;

		publicPhoto.DeletePhotoById($scope.photos[index].photoID).then(function(res) {
			$scope.processing = false;
			$scope.removeIndex = -1;
			$scope.editIndex = -1;
			$scope.photos.splice(index, 1);
		});
	};

	$scope.test = function() {
		$scope.valid = true;
		if(!$scope.form.name || !$scope.form.description || $scope.form.price === undefined) {
			$scope.valid = false;
		}
	};
};

module.exports = manage;
