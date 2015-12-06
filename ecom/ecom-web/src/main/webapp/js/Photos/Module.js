var angular = require('angular');
var ecomApp = require('./../app');

var wishListController = require("./WishList/wishListController");
ecomApp.controller('wishListController', wishListController);

var thumbDir = require('./Thumbnail/ThumbnailDirective');
ecomApp.directive('thumbnail', thumbDir);

