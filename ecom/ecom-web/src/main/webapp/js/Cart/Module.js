var angular = require('angular');
var ecomApp = require('./../app');

/**
 * Add to cart
 */
var addToCartDir = require('./addToCart/addToCartDirective');
ecomApp.directive('addToCartComponent', addToCartDir);

/**
 * Service
 */
var cartService  = require('./addToCart/cartService');
ecomApp.factory('cartService', cartService);

/**
 * Details
 */
var detailsCartController = require("./detailsCart/detailsController");
ecomApp.controller('detailsCartController', detailsCartController);