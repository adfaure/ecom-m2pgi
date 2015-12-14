var angular = require('angular');

var gallery = function($scope, $location, apiToken, publicPhoto) {
    var user;

    if(apiToken.isAuthentificated()) {
        user = apiToken.getUser();
    }

    publicPhoto.getBoughtPhoto(user.memberID).then(function(res) {
            $scope.photos = res;
        }
    );

};

module.exports = gallery;
