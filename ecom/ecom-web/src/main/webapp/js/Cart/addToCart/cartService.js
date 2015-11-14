var angular = require('angular');

var cartService =  function($http, apiToken, alertService) {
    service = {};
    service.addToCart    = addToCart;
    service.removeToCart = removeToCart;
    service.clearCart    = clearCart;
    service.validateCart = validateCart;
    return service;

    function addToCart(photo) {
        if (!apiToken.isAuthentificated()) {
            alertService.add("alert-danger", "You need to be logged ", 3000);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/members/id/' + usr.memberID + "/cart/photo/id/" + photo.photoID;
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
            var route = 'api/members/id/' + usr.memberID + "/cart/photo/id/" + photo.photoID;
            return $http.delete(route).then(handleSuccess("Photo supprimé au panier avec success"),
                handleError("Erreur lors de la suppression de l'article au panier")
            );
        }
    }

    function clearCart() {
        if (!apiToken.isAuthentificated()) {
            alertService.add("alert-danger", "You need to be logged ", 3000);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/members/id/' + usr.memberID + "/cart";
            return $http.delete(route).then(handleSuccess("Panier vidé avec success"),
                handleError("Erreur l'ors de la du panier")
            );
        }
    }

    function validateCart() {
        if (!apiToken.isAuthentificated()) {
            alertService.add("alert-danger", "You need to be logged ", 3000);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/orders/customer/login/' + usr.login  ;
            return $http.post(route, usr.cart).then(handleSuccess("Panier validé avec success"),
                handleError("Erreur l'ors de la du panier")
            )
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