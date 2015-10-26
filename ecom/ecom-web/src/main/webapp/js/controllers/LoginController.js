var angular = require('angular');

var loginController = function($scope, $location, authentificationService, apiToken) {

    if(apiToken.isAuthentificated()) $location.path("/");

    $scope.logInto = function() {
        authentificationService.login($scope.login, $scope.password).then(
            function(res) {
                console.log(res);
                if(res.success) {
                    $location.path("/");
                } else {
                    console.log("retry");
                }
            }
        );
    }
};

module.exports = loginController;