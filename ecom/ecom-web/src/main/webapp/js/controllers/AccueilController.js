var angular = require('angular');

var accueilController = function($scope, $location, apiToken, publicPhoto) {
    var cachedPhotos = [];
    $scope.hovering = false;


    var user;

    if(apiToken.isAuthentificated()) {
        user = apiToken.getUser();
    }


    publicPhoto.GetAll().then(function(res) {
            $scope.photos = cachedPhotos = res;
        }
    );

    $scope.tags = [
        { text: 'just' },
        { text: 'some' },
        { text: 'cool' },
        { text: 'tags' }
    ];

};

module.exports = accueilController;
