var angular = require('angular');

sellerService.$inject = ['$http'];

function sellerService($http) {
    var service = {};

    service.GetById = GetById;
    service.GetByUsername = GetByUsername;
    service.Create = Create;
    service.CreateFromMember = CreateFromMember;
    service.Update = Update;
    service.Delete = Delete;

    return service;

    function GetById(id) {
        return $http.get('api/sellers/' + id).then(handleSuccess, handleError('Error getting user by id'));
    }

    function GetByUsername(username) {
        return $http.get('api/sellers/login/' + username).then(handleSuccess, handleError('Error getting user by username'));
    }

    function Create(user) {
        var newUser = parseSeller(user);
        if (newUser != null)
            return $http.post('api/sellers', user).then(handleSuccess, handleError('Error creating user'));
        return {success: false, message: "not valid seller"};
    }

    function CreateFromMember(user) {
        var newUser = parseSeller(user);
        if (newUser != null)
            return $http.post('api/sellers/upgrade', user).then(handleSuccess, handleError('Error creating user'));
        return { success: false, message: "not valid seller"};
    }

    function Update(user) {
        var newUser = parseSeller(user);
        if (newUser != null)
            return $http.put('api/sellers/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        return {success: false, message: "not valid seller"};

    }

    function Delete(id) {
        return $http.delete('api/sellers/' + id).then(handleSuccess, handleError('Error deleting user'));
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

    function parseSeller(user) {
        var validUser = {
            email: "",
            firstName: "",
            lastName: "",
            accountType: 'S',
        };

        if (!user.login) return null;
        if (!user.password && user.accountType != 'M') return null;
        if (!user.RIB) return null;

        validUser.RIB = user.RIB;
        validUser.login = user.login;
        validUser.email = user.email;
        validUser.firstNale = user.firstName;
        validUser.lastName = user.lastName;
        validUser.password = user.password;

        return validUser;
    }
};

module.exports = sellerService;