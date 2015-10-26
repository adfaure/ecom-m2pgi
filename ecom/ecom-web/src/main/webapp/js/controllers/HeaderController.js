var angular = require('angular');

var headerController = function($scope, apiToken) {
    $scope.auth = false;
    $scope.$watch(
        apiToken.isAuthentificated, function(isAuth) {
            $scope.auth = isAuth;
            if($scope.auth) {
                $scope.user = apiToken.getUser();
                console.log($scope.user);
            }
        }
    )
};

module.exports = headerController;