var angular = require('angular');

var manage = function($scope, $location, $routeParams, $filter, publicPhoto, apiToken) {

	$scope.form = {
			id : '',
			name : '',
			description : '',
			price : 0
	};
	var cachedPhotos = [];
	$scope.photos = [];
	$scope.highlight = -1;
	$scope.query = '';

	publicPhoto.GetUserPhotos(apiToken.getUser().login).then(function(res) {
		$scope.photos = cachedPhotos = res;

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
		$scope.form.id = $scope.photos[index].photoID;
		$scope.form.name = $scope.photos[index].name;
		$scope.form.description = $scope.photos[index].description;
		$scope.form.price = $scope.photos[index].price;
		$scope.editIndex = index;
	};


	$scope.save = function(index) {
		$scope.processing = true;

		publicPhoto.Update($scope.form).then(function(res) {
			$scope.photos[index].name = $scope.form.name;
			$scope.photos[index].description = $scope.form.description;
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

	$scope.$on('search', function(event, data) {
		$scope.query = data.query;
		if($scope.editIndex != -1 || $scope.processing) return;
		if (!data.query) $scope.photos = cachedPhotos;
		//$scope.photos = $filter('filter')(cachedPhotos, {description: data.query});
		$scope.photos = $filter('matchQueries')(cachedPhotos, data.query);
	});
};

module.exports = manage;
