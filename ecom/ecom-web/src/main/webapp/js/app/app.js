define([ 'angular' , './controllers/HelloWorldController'], function (angular, hwController) {
	
	var ecomApp = angular.module('ecomApp',[]);
	ecomApp.controller('HelloWorldController', ['$scope', hwController]);
	
});
