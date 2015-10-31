/** JQuery */
var $ = jQuery = require('jquery');

/** Bootstrap */
var bootsrap = require('bootstrap');

/** Angular deps */
var angular = require('angular');
var angularRoute = require('angular-route');

/**
 * Controllers
 */
var mainController        = require('./controllers/MainController');
var inscriptionController = require('./controllers/InscriptionController');
var loginController       = require('./controllers/LoginController');
var headerController      = require('./controllers/HeaderController');
var accountDetails        = require('./controllers/AccountDetailsController')
var upgradeController     = require('./controllers/accountDetails/upgradeAccount')
/**
 * Services
 */
var memberService = require('./services/MemberService');
var sellerService = require('./services/SellerService');
var httpInterceptor = require('./services/HttpInterceptor');
var apiToken = require('./services/ApiToken');
var authentificationService = require('./services/AuthentificationService');


/**
 *
 * @type {module}
 */
var ecomApp = angular.module('ecomApp', ['ngRoute']);

ecomApp.config(function ($routeProvider, $httpProvider) {

    $routeProvider
        .when('/inscription', {
            templateUrl: './js/templates/inscription.html',
            controller: 'inscriptionController'
        })
        .when('/login', {
            templateUrl: './js/templates/login.html',
            controller: 'loginController'
        })
        .when('/profil', {
            templateUrl: './js/templates/accountDetails.html',
            controller: 'accountDetails'
        })
        .otherwise({
            redirectTo: '/'
        });

    $httpProvider.interceptors.push('httpInterceptor', httpInterceptor);

});

ecomApp.factory('memberService', memberService);
ecomApp.factory('sellerService', sellerService);
ecomApp.factory('authentificationService', authentificationService);
ecomApp.factory('apiToken', apiToken);
ecomApp.factory('httpInterceptor', httpInterceptor);



ecomApp.controller('inscriptionController', inscriptionController);
ecomApp.controller('mainController', mainController);
ecomApp.controller('loginController', loginController);
ecomApp.controller('headerController', headerController);
ecomApp.controller('accountDetails', accountDetails);
ecomApp.controller('upgradeController', upgradeController);


module.exports = ecomApp;
