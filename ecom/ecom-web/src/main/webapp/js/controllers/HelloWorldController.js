var angular = require('angular');

var HelloWorldController = function($scope, $http) {
  $http.get("rest/hello").success(function(res) {
	  console.log(res);
	  $scope.message = res.message;
  });
};

module.exports = HelloWorldController;