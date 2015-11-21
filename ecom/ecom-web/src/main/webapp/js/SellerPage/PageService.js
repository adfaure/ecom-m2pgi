var angular = require('angular');

pageService.$inject = ['$http'];

function pageService($http) {
    var service = {};
    service.getPage          = getPage;
    service.updatePage       = updatePage;
    service.getPageWithLogin = getPageWithLogin;
    return service;

    function getPage(sellerId) {
        return $http.get('api/sellers/page/'+ sellerId).then(handleSuccess, handleError('Error getting seller page'));
    }

    function getPageWithLogin(sellerLogin) {
        return $http.get('api/sellers/page/login/'+ sellerLogin).then(handleSuccess, handleError('Error getting seller page'));
    }

    function updatePage(sellerID, page) {
        return $http.post('api/sellers/page/'+ sellerID, page).then(handleSuccess, handleError('Error updating seller page'));

    }

    // private functions

    function handleSuccess(res) {
        return {data : res.data, success : true};
    }

    function handleError(error) {
        return function () {
            return { success: false, message: error };
        };
    }

};

module.exports = pageService;