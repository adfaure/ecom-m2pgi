var angular = require('angular');

var gallery = function($scope, $location, apiToken, publicPhoto) {
    var user;
    var photo = [];
    if(!apiToken.isAuthentificated()) {
        $location.path('/');
    } else {
        user = apiToken.getUser();
    }

    publicPhoto.getBoughtPhoto(user.memberID).then(function(res) {
            $scope.photos = res;
        }
    );

};

module.exports = gallery;
