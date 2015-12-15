var angular = require('angular');

sellerService.$inject = ['$http'];

function sellerService($http) {
    var service = {};

    service.GetById = GetById;
    service.GetByUsername = GetByUsername;
    service.GetCount = GetCount;
    service.GetTopSellers = GetTopSellers;
    service.Create = Create;
    service.CreateFromMember = CreateFromMember;
    service.Update = Update;
    service.Delete = Delete;
    service.getOrderBySellerId = getOrderBySellerId;
    return service;

    function GetById(id) {
        return $http.get('api/sellers/id/' + id).then(handleSuccess, handleError('Error getting user by id'));
    }

    function GetByUsername(username) {
        return $http.get('api/sellers/login/' + username).then(handleSuccess, handleError('Error getting user by username'));
    }

    function GetCount(){
    	return $http.get('api/sellers/count').then(handleSuccess, handleError('Error getting the total number of sellers'));
    }

    function GetTopSellers(){
    	return $http.get('api/sellers/top10').then(handleSuccess, handleError('Error getting the top 10 sellers'));
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
               return $http.put('api/sellers/update/id/' + user.memberID, user).then(handleSuccess, handleError('Error updating user'));
               var defer = $q.defer();
               defer.resolve({success: false, message: "not valid seller"})
           return defer.promise;
       }




    function getOrderBySellerId(userID) {
        return $http.get('api/sellers/id/' + userID + '/orders').then(handleSuccess, handleError('Error getting orders'));
    }

    function Delete(id) {
        return $http.delete('api/sellers/id/' + id).then(handleSuccess, handleError('Error deleting user'));
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
        if (!user.sellerInfo &&  !user.sellerInfo.rib) return null;

        validUser.RIB = user.rib;
        validUser.login = user.login;
        validUser.email = user.email;
        validUser.firstNale = user.firstName;
        validUser.lastName = user.lastName;
        validUser.password = user.password;

        return validUser;
    }

    function validSeller(user){

    	if (!user.memberID)
    		return false;
    	else
    		return true;

    }
};

module.exports = sellerService;
