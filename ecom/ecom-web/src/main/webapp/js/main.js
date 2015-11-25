/** JQuery */
var $ = jQuery = require('jquery');

/** Bootstrap */
var bootsrap = require('bootstrap');

/** Angular deps */
var angular        = require('angular');
var angularRoute   = require('angular-route');
var angularAnimate = require('angular-animate');

/**
 * Controllers
 */
var mainController             = require('./controllers/MainController');
var inscriptionController      = require('./controllers/InscriptionController');
var loginController            = require('./controllers/LoginController');
var headerController           = require('./controllers/HeaderController');
var accountDetails             = require('./controllers/AccountDetailsController');
var upgradeController          = require('./controllers/accountDetails/upgradeAccount');
var uploadController           = require('./controllers/accountDetails/uploadPhoto');
var managePhotosController     = require('./controllers/accountDetails/managePhotos');
var accueilController          = require('./controllers/AccueilController');
var detailsPhotoController     = require('./controllers/DetailsPhotoController');
var administratorController    = require('./controllers/AdministratorController');
var searchController		   = require('./controllers/SearchController');
var navsidebarController	   = require('./controllers/NavsidebarController');

/**
 * Services
 */
var memberService = require('./services/MemberService');
var sellerService = require('./services/SellerService');
var localService = require('./services/LocalService');
var httpInterceptor = require('./services/HttpInterceptor');
var apiToken = require('./services/ApiToken');
var authentificationService = require('./services/AuthentificationService');
var uploadPhoto  = require('./services/uploadPhoto');
var publicPhoto  = require('./services/PublicPhotoService');
var alertService = require('./services/AlertService');

/**
 * Directives
 */
var inputFileDir = require('./directives/InputFile');

/**
 * Modules
 */
var cartModule  = require('./Cart/Module');
var orderModule = require('./Orders/Module');
var pageModule  = require('./SellerPage/Module');

/**
 *
 * @type {module}
 */

var ecomApp = require("./app.js");

ecomApp.config(function ($routeProvider, $httpProvider) {

    $routeProvider
        .when('/accueil', {
            templateUrl : './js/templates/accueil/accueil.html',
            controller : 'accueilController'
        })
        .when('/search', {
            templateUrl : './js/templates/searchResult.html',
            controller : 'searchController'
        })
        .when('/photos/details/:id', {
            templateUrl : './js/templates/photoDetails.html',
            controller : 'detailsPhotoController'
        })
        .when('/inscription', {
            templateUrl: './js/templates/inscription.html',
            controller: 'inscriptionController'
        })
        .when('/login',  {
            templateUrl: './js/templates/login.html',
            controller: 'loginController'
        })
        .when('/profil', {
            templateUrl: './js/templates/accountDetails.html',
            controller: 'accountDetails'
        })
        .when('/profil/:section', {
            templateUrl: './js/templates/accountDetails.html',
            controller: 'accountDetails'
        })
        .when('/administrator', {
        	templateUrl: './js/templates/administratorTemplate.html',
        	controller: 'administratorController'
        })
        .when('/', {
            redirectTo: '/accueil',
        })
        .when('/seller/page/:id', {
            templateUrl : './js/SellerPage/Page/PageTemplate.html',
            controller : 'pageController'
        })
        .otherwise({
        	redirectTo: '/accueil',
        });

    $httpProvider.interceptors.push('httpInterceptor', httpInterceptor);

});

ecomApp.directive('ecomInputFile', inputFileDir);

ecomApp.factory('memberService', memberService);
ecomApp.factory('sellerService', sellerService);
ecomApp.factory('localService', localService);
ecomApp.factory('authentificationService', authentificationService);
ecomApp.factory('apiToken', apiToken);
ecomApp.factory('httpInterceptor', httpInterceptor);
ecomApp.factory('uploadPhoto', uploadPhoto);
ecomApp.factory('publicPhoto', publicPhoto);
ecomApp.factory('alertService', alertService);

ecomApp.controller('inscriptionController', inscriptionController);
ecomApp.controller('mainController', mainController);
ecomApp.controller('loginController', loginController);
ecomApp.controller('accountDetails', accountDetails);
ecomApp.controller('upgradeController', upgradeController);
ecomApp.controller('uploadPhoto', uploadController);
ecomApp.controller('managePhotos', managePhotosController);
ecomApp.controller('accueilController', accueilController);
ecomApp.controller('detailsPhotoController', detailsPhotoController);
ecomApp.controller('administratorController', administratorController);
ecomApp.controller('searchController', searchController);
ecomApp.controller('navsidebarController', navsidebarController);
