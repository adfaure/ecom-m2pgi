var angular = require('angular');

var cartService =  function($http, $q, $sce, $location ,apiToken, alertService) {
    var sessionCart = [];

    service = {};
    service.addToCart    = addToCart;
    service.removeToCart = removeToCart;
    service.clearCart    = clearCart;
    service.validateCart = validateCart;
    service.getCart      = getCart;
    return service;

    function addToCart(photo) {
        if (!apiToken.isAuthentificated()) { //si l'utilisateur est !auth on ajoute la photo si elle n'y est pas déja
            if(sessionCart.findIndex(function(elem) {
                    return (photo.photoID == elem.photoID);
                }) == -1 ) {
                sessionCart.push(photo);
            }
            return promiseResolver(sessionCart);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/members/id/' + usr.memberID + "/cart/photo/id/" + photo.photoID;
            return $http.post(route).then(function(elem) {
                    if(usr.cart.findIndex(function(elem) {
                            return (photo.photoID == elem.photoID);
                        }) == -1 )
                    {
                        usr.cart.push(photo);
                    }
                return elem;
            }, handleError("Erreur lors de l'ajout au panier")
          ).then(handleSuccess("Photo ajoutée au panier avec success"));
        }
    }

    function addAllToCart(photoArray) {

    }

    function getCart() {
        if (!apiToken.isAuthentificated()) {
            return sessionCart;
        } else {
            return apiToken.getUser().cart;
        }
    }


    function removeToCart(photo) {
        if (!apiToken.isAuthentificated()) {
            sessionCart = sessionCart.filter(function(elem) {
                return !(photo.photoID == elem.photoID);
            });
            return promiseResolver(sessionCart);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/members/id/' + usr.memberID + "/cart/photo/id/" + photo.photoID;
            return $http.delete(route).then(function(res) {
                usr.cart = usr.cart.filter(function(elem) {
                    return !(photo.photoID == elem.photoID);
                });
                return res;
            }, handleError("Erreur lors de la suppression de l'article au panier")
            ).then(handleSuccess("Photo supprimé au panier avec success"));
        }
    }

    function clearCart() {
        if (!apiToken.isAuthentificated()) {
            sessionCart = [];
            return promiseResolver(sessionCart);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/members/id/' + usr.memberID + "/cart";
            return $http.delete(route).then(function(res) {
                usr.cart = [];
                return res;
            }, handleError("Erreur l'ors de la du panier")
            ).then(handleSuccess("Panier vidé avec success"));
        }
    }

    function validateCart() {
        if (!apiToken.isAuthentificated()) {
            alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour effectuer cette action ...</strong>"), 3000);
        } else {
            var usr = apiToken.getUser();
            var route = 'api/orders/customer/login/' + usr.login  ;
            if(usr.cart.length == 0) {
                var defer = $q.defer();
                defer.resolve();
                return defer.promise.then(handleError("Attention, vous ne pouvez pas valider un panier vide"));
            }
            var cart = parseCart( usr.cart);
            return $http.post(route, cart).then(handleSuccess("Panier validé avec succès"),
                handleError("Erreur lors de la validation du panier")
            )
        }
    }

    function handleSuccess(message) {
        return function(res) {
            alertService.add("alert-success", $sce.trustAsHtml("<strong>"+message+"</strong>"), 3000);
            return { success : true, data : res.data };
        }

    }

    function handleError(error) {
        return function(res) {
            alertService.add("alert-danger" , $sce.trustAsHtml("<strong>"+error+"</strong>"), 3000);
            return { success :false, error : res };
        }
    }


    function promiseResolver(elem) {
        var pr = $q.defer();
        pr.resolve(elem);
        return pr.promise;
    }

    function parseCart(cart) {
        return cart.map(function(photo) {
            return {
                sellerID: photo.sellerID,
                photoID : photo.photoID,
                price : photo.price
            }
        });
    }
};

module.exports = cartService;
