var angular = require('angular');

var loginController = function($scope, $location, authentificationService, apiToken) {

    if(apiToken.isAuthentificated()) $location.path("/");

    $scope.logInto = function() {
        authentificationService.login($scope.login, $scope.password).then(
            function(res) {
                if(res.success) {
                    $location.path("/");
                } else {
                }
            }
        );
    }
};

module.exports = loginController;