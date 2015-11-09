var angular = require('angular');

administratorService.$inject = ['$http'];

function administratorService($http) {
    var service = {};

    service.GetPhotoCount = GetPhotoCount;
   
    return service;

    function GetPhotoCount() {
        return $http.get('api/photos/count').then(handleSuccess, handleError('The photo count couldnt be accomplished'));
    }

    // private functions

    function handleSuccess(res) {
        return res.data;
    }

    function handleError(error) {
        return function () {
            return {success: false, message: error};
        };
    }
};

module.exports = administratorService;