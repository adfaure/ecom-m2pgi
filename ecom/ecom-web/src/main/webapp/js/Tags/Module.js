var angular = require('angular');
var ecomApp = require('./../app');

var TagsService = require('./TagsService');
ecomApp.factory('TagsService', TagsService);
