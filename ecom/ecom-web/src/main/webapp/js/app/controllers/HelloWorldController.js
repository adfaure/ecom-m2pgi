define([ 'angular' , './../app'], function (angular) {
	

	function HelloWorldController($scope) {
	  $scope.helloWorld = 'Hello world !';
	};
	
	HelloWorldController.$inject=['$scope'];
	
	return HelloWorldController;
});
