var angular = require('angular');
var ecomApp = require('./../app');

/**
 * Details
 */
var ordersController = require("./OrdersHistory/ordersController");
ecomApp.controller('ordersController', ordersController);


var orderService = require('./OrderService');
ecomApp.factory('orderService', orderService);
