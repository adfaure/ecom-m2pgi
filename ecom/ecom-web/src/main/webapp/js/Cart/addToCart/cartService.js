var angular = require('angular');

var cartService =  function($http, apiToken, alertService) {
    service = {};
    service.addToCart    = addToCart;
    service.removeToCart = removeToCart;
    service.clearCart    = clearCart;
    return service;

    function addToCart(photo) {
        if (!apiToken.isAuthentificated()) {
            alertService.add("alert-danger", "You need to be logged ", 3000);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/members/id/' + usr.login + "/cart/photo/id/" + photo.photoID;
            return $http.post(route).then(
                handleSuccess("Photo ajouté au panier avec success"),
                handleError("Erreur l'ors de l'ajout au panier")
            );
        }
    }

    function removeToCart(photo) {
        if (!apiToken.isAuthentificated()) {
            alertService.add("alert-danger", "You need to be logged ", 3000);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/members/id/' + usr.login + "/cart/photo/id/" + photo.photoID;
            return $http.delete(route).then(handleSuccess("Photo supprimé du panier avec success"),
                handleError("Erreur lors de la suppression de l'article au panier")
            );
        }
    }

    function clearCart() {
        if (!apiToken.isAuthentificated()) {
            alertService.add("alert-danger", "You need to be logged ", 3000);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/members/id/' + usr.login + "/cart";
            return $http.delete(route).then(handleSuccess("Panier vidé avec success"),
                handleError("Erreur lors de la suppréssion des éléments du panier")
            );
        }
    }

    function handleSuccess(message) {
        return function(res) {
            alertService.add("alert-success", message, 3000);
            return { success : true, data : res.data };
        }

    }

    function handleError(error) {
        return function(res) {
            alertService.add("alert-danger" , error, 3000);
            return { success :false, error : res };
        }
    }
};

module.exports = cartService;