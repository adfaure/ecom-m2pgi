var angular = require('angular');

module.exports = function localService() {

  var service = {};
  service.get = get;
  service.getObject = getObject;
  service.set = set;
  service.unset = unset;
  return service;

  function get(key) {
    return localStorage.getItem(key);
  }

  function getObject(key) {
    return JSON.parse(localStorage.getItem(key));
  }

  function set(key, val) {
    return localStorage.setItem(key, val);
  }

  function unset(key) {
    return localStorage.removeItem(key);
  }

};
