var angular = require('angular');

memberService.$inject = ['$http'];

function memberService($http) {
    var service = {};

    service.GetById = GetById;
    service.GetByUsername = GetByUsername;
    service.GetCount = GetCount;
    service.Create = Create;
    service.Update = Update;
    service.Delete = Delete;

    return service;

    function GetById(id) {
        return $http.get('api/members/' + id).then(handleSuccess, handleError('Error getting user by id'));
    }

    function GetByUsername(username) {
        return $http.get('api/members/login/' + username).then(handleSuccess, handleError('Error getting user by username'));
    }

    function GetCount(){
    	return $http.get('api/members/count').then(handleSuccess, handleError('Error getting the total number of members'));
    }
    
    function Create(user) {
    	var validUser = parseUser(user);
    	if(validUser != null)
    		return $http.post('api/members', user).then(handleSuccess, handleError('Error creating user'));
    	return { success : false, message : "not valid user"};
    }

    function Update(user) {
    	var validUser = parseUser(user);
    	if(validUser != null)
    		return $http.put('api/members/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
    	return {success : false, message : "not valid user"};
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
    
    function parseUser(user) {
    	var validUser = {
	                     email: "",
	                     firstName: "",
	                     lastName: "",
	                     accountType: 'M'
	                 };
    	
    	if(!user.login) return null;
    	if(!user.password) return null;
    	
    	validUser.login = user.login;
    	validUser.email = user.email;
    	validUser.firstNale = user.firstName;
    	validUser.lastName = user.lastName;
    	validUser.password = user.password;
    	return validUser;
    }
    
};

module.exports = memberService;