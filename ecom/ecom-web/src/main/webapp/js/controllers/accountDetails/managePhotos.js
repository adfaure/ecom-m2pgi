var angular = require('angular');

var manage = function($scope, $location , $routeParams, $filter,  $interval, TagsService, publicPhoto, apiToken) {

	$scope.form = {
			id : '',
			name : '',
			description : '',
			tags : [],
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
				$interval(function() {
					$scope.highlight = -1;
				}, 1500, 1);
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
		if($scope.photos[index].tags == "") {
			$scope.form.tags = [];
		} else {
			$scope.form.tags = $scope.photos[index].tags.split(" ");
		}
		$scope.form.price = $scope.photos[index].price;
		$scope.editIndex = index;
	};


	$scope.save = function(index) {
		$scope.processing = true;
		var photo = {};
		photo.id = $scope.form.id;
		photo.name = $scope.form.name;
		photo.description = $scope.form.description;
		photo.tags = $scope.form.tags.map(function(tag) {return tag.name}).join(" ").trim();
		console.log(photo.tags);
		photo.price = $scope.form.price;


		publicPhoto.Update(photo).then(function(res) {
			$scope.valid = false;
			$scope.photos[index].name = photo.name;
			$scope.photos[index].description = photo.description;
			$scope.photos[index].tags = photo.tags;
			$scope.photos[index].price = photo.price;
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
			console.log("tes jambon!");
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

	var autoComp = new  TagsService.autoComplete();
	$scope.load = function(query) {
		return autoComp.get(query);
	}
};

module.exports = manage;
