/** Angular deps */
var angular = require('angular');
var angularRoute = require('angular-route');
var angularAnimate = require('angular-animate');
var chart = require('angular-chart.js');

/**
 *
 * @type {module}
 */
var ecomApp = angular.module('ecomApp', ['ngRoute', 'ngAnimate', 'chart.js']);

module.exports = ecomApp;