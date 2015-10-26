var angular = require('angular');

function apitoken() {
    var apitoken = {};
    apitoken.token = "";
    apitoken.user  = null;
    apitoken.isAuthentificated = isAuthentificated;
    apitoken.setToken = setToken;
    apitoken.getToken = getToken;
    apitoken.getUser = getUser;
    apitoken.setUser = setUser;
    return apitoken;

    function isAuthentificated() {
        return (apitoken.token !== "");
    }

    function setToken(token) {
        apitoken.token = token;
    }

    function getToken(token) {
        return apitoken.token;
    }

    function getUser() {
        return apitoken.user;
    }

    function setUser(user) {
        apitoken.user = user;
    }

};

module.exports = apitoken;
