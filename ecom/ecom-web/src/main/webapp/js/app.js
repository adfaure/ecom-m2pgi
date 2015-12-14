/** Angular deps */
var angular = require('angular');
var angularRoute = require('angular-route');
var angularAnimate = require('angular-animate');
var chart = require('angular-chart.js');
var angularMessages = require('angular-messages');
var tags = require('ng-tags-input');
/**
 *
 * @type {module}
 */
var ecomApp = angular.module('ecomApp', ['ngRoute', 'ngAnimate', 'chart.js', 'ngMessages', 'ngTagsInput']);

module.exports = ecomApp;