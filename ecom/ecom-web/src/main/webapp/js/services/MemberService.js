var angular = require('angular');

memberService.$inject = ['$http'];

function memberService($http) {
    var service = {};

    service.GetById = GetById;
    service.GetByUsername = GetByUsername;
    service.Create = Create;
    service.Update = Update;
    service.Delete = Delete;

    return service;

    function GetById(id) {
        return $http.get('api/members/' + id).then(handleSuccess, handleError('Error getting user by id'));
    }

    function GetByUsername(username) {
        console.log($http.get('api/members/login/' + username).then(handleSuccess, handleError('Error getting user by id')));
        return $http.get('api/members/login/' + username).then(handleSuccess, handleError('Error getting user by username'));
    }

    function Create(user) {
        return $http.post('api/members', user).then(handleSuccess, handleError('Error creating user'));
    }

    function Update(user) {
        return $http.put('api/members/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
    }

    function Delete(id) {
        return $http.delete('api/members/' + id).then(handleSuccess, handleError('Error deleting user'));
    }

    // private functions

    function handleSuccess(res) {
        return res.data;
    }

    function handleError(error) {
        return function () {
            return { success: false, message: error };
        };
    }
};

module.exports = memberService;