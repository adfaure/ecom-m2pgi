var angular = require('angular');

publicPhoto.$inject = ['$http'];

function publicPhoto($http) {
    var service = {};

    service.GetById = GetById;
    service.GetAll = GetAll;

    return service;

    function GetById(id) {
        return $http.get('api/photos/id/' + id).then(handleSuccess, handleError('Error getting photo by id'));
    }

    function GetAll() {
        return $http.get('api/photos/').then(handleSuccess, handleError('Error getting all photos'));
    }

    // private functions

    function handleSuccess(res) {
        return res.data;
    }

    function handleError(error) {
        return {success: false, message: error};
    }
};

module.exports = publicPhoto;