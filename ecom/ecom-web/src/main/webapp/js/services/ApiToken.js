var angular = require('angular');

module.exports = function apitoken() {
    var service = {};
    service.token = "";
    service.user  = null;
    service.isAuthentificated = isAuthentificated;
    service.setToken = setToken;
    service.getToken = getToken;
    service.getUser = getUser;
    service.setUser = setUser;
    return service;

    function isAuthentificated() {
        return (service.token !== "");
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

