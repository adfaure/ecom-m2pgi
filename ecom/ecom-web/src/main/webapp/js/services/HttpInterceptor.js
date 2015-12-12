var angular = require('angular');

function headerTokenInterceptor($q, $location, apiToken, localService) {
  return {
    'request': function (config) {
      if(apiToken.isAuthentificated()) {
        config.headers['auth_token'] = apiToken.getToken();
      }
      return config;
    },
    // This is the responseError interceptor
    responseError: function(rejection) {
      if (rejection.status === 401) {
        apiToken.setToken(null);
        apiToken.setUser(null);
        localService.unset('auth_token');
        localService.unset('user');
        $location.path("/login");
      }

      /* If not a 401, do nothing with this error.
      * This is necessary to make a `responseError`
      * interceptor a no-op. */
      return $q.reject(rejection);
    }
  };
};

module.exports = headerTokenInterceptor;
