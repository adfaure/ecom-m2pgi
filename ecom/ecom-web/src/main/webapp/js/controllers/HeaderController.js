var angular = require('angular');

var headerController = function($scope, apiToken, authentificationService) {
    $scope.auth = false;
    $scope.$watch(
        apiToken.isAuthentificated, function(isAuth) {
            $scope.auth = isAuth;
            if($scope.auth) {
                $scope.user = apiToken.getUser();
            }
        }
    );

    $scope.logout = authentificationService.logout;
};

module.exports = headerController;