var angular = require('angular');

module.exports = function apitoken(localService) {
    var service = {};
    service.token = localService.get('authtoken') !== null ? localService.get('authtoken') : null;
    service.user  = localService.get('user') !== null ? angular.fromJson(localService.get('user')) : null;
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
        localService.set('authtoken', token);
    }

    function getToken(token) {
        return service.token;
    }

    function setUser(user) {
        service.user = user;
        localService.set('user', JSON.stringify(user));
    }

    function getUser() {
        return service.user;
    }
};
