var angular = require('angular');


var upgrade = function($scope, $location, apiToken, sellerService) {
    $scope.RIB    = "";
    $scope.submit = submit;

    var user   = apiToken.getUser();

    if($scope.user.accountType == 'S') {
        $scope.subview = 'details';
    }

    function submit() {
        if($scope.RIB != "") {
            user.RIB = $scope.RIB;
            sellerService.CreateFromMember(user).then(function(data) {
                if(data.success) {
                    apiToken.setUser(data.user);
                    $scope.setView('details');
                }
            });
        }
    }
};

module.exports = upgrade;