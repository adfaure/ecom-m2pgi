var angular = require('angular');

module.exports = function alertService($http, $q) {
    var prop;

    var service = {
        get : get
    };

    return service;

    function get(name) {
        if(typeof prop === "undefined" ) {
            return $http.get("properties.json").then(function (res) {
                prop = res.data;
                return prop[name];
            });
        } else {
            var defer = $q.defer();
            defer.resolve(prop[name]);
            return defer.promise;
        }
    }
};

