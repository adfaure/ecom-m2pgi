var angular = require('angular');
var angularRoute = require('angular-route');

var mainController = require('./controllers/MainController');
var hwController = require('./controllers/HelloWorldController');

var ecomApp = angular.module('ecomApp', ['ngRoute']);


ecomApp.config(function ($routeProvider) {

    $routeProvider
        .when('/', {
            templateUrl: './js/templates/mainTemplate.html',
            controller: 'MainController'
        })
        .when('/helloWorld', {
            templateUrl: './js/templates/helloWorld.html',
            controller: 'HelloWorldController'
        })
        .otherwise({
            redirectTo: '/'
        });
});

ecomApp.controller('HelloWorldController', hwController);

ecomApp.controller('MainController', mainController);

module.exports = ecomApp;
