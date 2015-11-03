var angular = require('angular');

var accueilController = function($scope, publicPhoto) {

    publicPhoto.GetAll().then(function(res) {
            console.log(res);
            $scope.photos = res;
        }
    );

};

module.exports = accueilController;