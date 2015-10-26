var angular = require('angular');

function loginService($http, apiToken) {
    service = {};
    service.login = login;
    service.logout = logout;
    return service;

    function login(username, password) {
        var data = angular.element.param(
            { password : password }
        );

        return $http({
            method : 'POST',
            url : 'api/auth/login/' + username,
            data : data,
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(handleLoginSuccess, handleError('cannot login'));
    }

    function logout() {
        return $http({
            method : 'POST',
            url : 'api/auth/logout',
            data : {} ,
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(handleLogOutSuccess, handleError('cannot logout'));
    }

    function handleLoginSuccess(res) {
        apiToken.setToken(res.data.token);
        apiToken.setUser(res.data.user);
        return {success : true }; // FIXME shall we return something here ?
    }

    function handleLogOutSuccess(res) {
        console.log("loged out");
        apiToken.setToken("");
        apiToken.setUser({});
        return res;
    }

    function handleError(error) {
        return function () {
            return { success: false, message: error };
        };
    }
};

module.exports = loginService;