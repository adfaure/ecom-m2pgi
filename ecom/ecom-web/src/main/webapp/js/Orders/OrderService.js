var angular = require('angular');

orderService.$inject = ['$http'];

function orderService($http) {
    var service = {};

    service.GetOrderCount = GetOrderCount;
    service.GetTotalPurchaseCost = GetTotalPurchaseCost;
    service.GetOrdersWithSellerLogin = GetOrdersWithSellerLogin;

    return service;

    function GetOrderCount() {
        return $http.get('api/orders/count').then(handleSuccess, handleError('Error getting number of purchases'));
    }
    
    function GetTotalPurchaseCost() {
        return $http.get('api/orders/totalPurchaseCost').then(handleSuccess, handleError('Error getting the total purchase cost'));
    }

    function GetOrdersWithSellerLogin(login) {
        return $http.get('api/orders/customer/login/' + login).then(handleSuccess, handleError('Error getting the total purchase cost'));
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

module.exports = orderService;