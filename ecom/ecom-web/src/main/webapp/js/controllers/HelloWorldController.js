var angular = require('angular');

var HelloWorldController = function($scope, $http) {
  $http.get("rest/hello").success(function(res) {
	  $scope.helloWorld = res.hello;
  });
};

module.exports = HelloWorldController;