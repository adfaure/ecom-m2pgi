
var angular = require('angular');
var ecomApp = require('./../app');

var pageController = require("./Page/PageController");
ecomApp.controller('pageController', pageController);

var managePageController = require("./ManagePage/ManagePageController");
ecomApp.controller('managePageController', managePageController);


var pageService = require("./PageService");
ecomApp.factory('pageService', pageService);