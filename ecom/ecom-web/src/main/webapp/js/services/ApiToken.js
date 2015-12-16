var angular = require('angular');

module.exports = function apitoken(localService) {
    var service = {};
    service.token = localService.get('authtoken') !== null ? localService.get('authtoken') : null;
    service.user  = {};
    service.isAuthentificated = isAuthentificated;
    service.setToken = setToken;
    service.getToken = getToken;
    service.getUser = getUser;
    service.setUser = setUser;

    return service;

    function isAuthentificated() {
        return service.token !== null;
    }

    function setToken(token) {
        service.token = token;
        if(token === null) {
            localService.clear();
        } else {
            localService.set('authtoken', token);
        }
    }

    function getToken(token) {
        return service.token;
    }

    function setUser(user) {
        service.user = user;
    }

    function getUser() {
        return service.user;
    }
};
