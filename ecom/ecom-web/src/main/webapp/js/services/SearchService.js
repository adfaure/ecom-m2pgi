var angular = require('angular');

module.exports = function searchService() {
  var service = {};
  service.who = null;
  service.setWho = setWho;
  service.getWho = getWho;
  return service;

  function setWho(me) {
    service.who = me;
  }

  function getWho(token) {
    return service.who;
  }
};
