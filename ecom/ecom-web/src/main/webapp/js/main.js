/** JQuery */
var $ = jQuery = require('jquery');

/** Bootstrap */
var bootsrap = require('bootstrap');

/** Angular deps */
var angular      = require('angular');
var angularRoute = require('angular-route');

/**
 * Controllers
 */
var mainController        = require('./controllers/MainController');
var hwController          = require('./controllers/HelloWorldController');
var inscriptionController = require('./controllers/InscriptionController')

/**
 * Services
 */
var memberService = require('./services/MemberService')

/**
 *
 * @type {module}
 */
var ecomApp = angular.module('ecomApp', ['ngRoute']);


ecomApp.config(function ($routeProvider) {

    $routeProvider
        .when('/', {
            templateUrl : './js/templates/mainTemplate.html',
            controller  : 'MainController'
        })
        .when('/inscription' , {
            templateUrl : './js/templates/inscription.html',
            controller  : 'inscriptionController'
        })
        .otherwise({
            redirectTo: '/'
        });
});

ecomApp.factory('memberService' , memberService );

ecomApp.controller('inscriptionController', inscriptionController);

ecomApp.controller('MainController', mainController);

module.exports = ecomApp;
