var angular = require('angular');

function headerTokenInterceptor($q, apiToken) {
    return {
        'request': function (config) {
            if(apiToken.isAuthentificated()) {
                config.headers['sessionToken'] = apiToken.getToken();
                console.log("interception !");
            }
            return config;
        }

    };
};

module.exports = headerTokenInterceptor;
