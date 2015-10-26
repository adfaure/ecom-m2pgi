var angular = require('angular');

function loginService($http, apiToken) {
    service = {};
    service.login = login;
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
        }).then(handleSuccess, handleError('cannot login'));
    }

    function handleSuccess(res) {
        apiToken.setToken(res.data.token);
        apiToken.setUser(res.data.user);
        return res.data; // FIXME shall we return something here ?
    }

    function handleError(error) {
        return function () {
            return { success: false, message: error };
        };
    }
};

module.exports = loginService;