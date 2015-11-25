var angular = require('angular');

module.exports = function apitoken(localService) {
    var service = {};
    service.token = localService.get('auth_token') !== null ? angular.fromJson(localService.get('auth_token')).token : null;
    service.user  = localService.get('auth_token') !== null ? angular.fromJson(localService.get('auth_token')).user : null;

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
    }

    function getToken(token) {
        return service.token;
    }

    function getUser() {
        return service.user;
    }

    function setUser(user) {
        service.user = user;
    }
};
