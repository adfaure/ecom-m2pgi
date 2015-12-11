/** Angular deps */
var angular = require('angular');
var angularRoute = require('angular-route');
var angularAnimate = require('angular-animate');
var chart = require('angular-chart.js');
var angularMessages = require('angular-messages');

/**
 *
 * @type {module}
 */
var ecomApp = angular.module('ecomApp', ['ngRoute', 'ngAnimate', 'chart.js', 'ngMessages']);

module.exports = ecomApp;