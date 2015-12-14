var angular = require('angular');
var ecomApp = require('./../app');

/**
 * Details
 */
var MemDetailsController = require("./MemDetailsController");
ecomApp.controller('MemDetailsController', MemDetailsController);


var galleryCtrl = require('./Gallery/GalleryController');
ecomApp.controller('galleryCtrl', galleryCtrl);