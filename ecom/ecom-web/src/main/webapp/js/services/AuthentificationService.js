var angular = require('angular');

function loginService($http, apiToken, localService) {
    service = {};
    service.login  = login;
    service.logout = logout;
    //service.refresh = refresh;
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
    };

    function logout() {
      apiToken.setToken(null);
      apiToken.setUser(null);
      // The backend doesn't care about logouts, delete the token and you're good to go.
      localService.unset('auth_token');
      localService.unset('user');
    };

    /*function refresh() {
      $http.post('api/auth/refresh').then(handleRefreshSuccess, handleError('cannot refresh session!'));
    };

    function handleRefreshSuccess(res) {
      console.log("refreshed!");
      console.log(res);
      apiToken.setToken(res.data.data.token);
      setTimeout(function(){ refresh(); }, 540000);
      return { success : true };
    };*/

    function handleLoginSuccess(res) {
      var data = res.data.data;
      apiToken.setToken(data.token);
      apiToken.setUser(data.user);

      // A client-side timer is created to call a service to renew the token before its expiring time.
      // The new token will replace the existing in future calls.
      //setTimeout(function(){ refresh(); }, 540000);
      return {success : true }; // FIXME shall we return something here ?
    };

    function handleLogOutSuccess() {
      return { success : true };
    };

    function handleError(error) {
        return function () {
            return { success: false, message: error };
        };
    };
};

module.exports = loginService;
