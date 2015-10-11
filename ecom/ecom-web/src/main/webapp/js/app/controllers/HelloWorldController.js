define([ 'angular' , './../app'], function (angular) {
	

	return HelloWorldController = function($scope, $http) {
	  $http.get("rest/hello").success(function(res) {
		  $scope.helloWorld = res.hello;
	  });
	};
	
});
