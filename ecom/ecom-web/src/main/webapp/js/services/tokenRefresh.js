var angular = require('angular');

tokenRefresh.$inject = ['$http'];

function tokenRefresh($http) {
    service = {};
    service.refresh = refresh;
    return service;

    function refresh() {
        return $http.post('api/auth/refresh').then(function(res) {
            return res.data;
        });
    }

}

module.exports = tokenRefresh;
