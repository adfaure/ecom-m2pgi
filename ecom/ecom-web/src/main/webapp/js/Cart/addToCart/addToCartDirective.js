var angular = require('angular');

var addToCart = function ($compile, apiToken, cartService) {
    return {
        restrict: 'E',
        scope : {
            photo : "=photo"
        },
        templateUrl: './js/Cart/addToCart/addToCartTemplate.html',
        controller : function ($scope ) {
            var user;

            if(apiToken.isAuthentificated()) {
                var updateCart = $scope.$watch(apiToken.getUser, function() {
                        if(!apiToken.isAuthentificated()) {
                            updateCart(); //stop the watch if the user is not logged in anymore
                            return;
                        }
                        user = apiToken.getUser();
                        $scope.alreadyInCart = (user.cart.find(function (elem, idx, array) {
                            return (elem.photoID == $scope.photo.photoID);
                        }) != undefined);
                    }
                );
            }

            $scope.clickAdd    = clickAdd;
            $scope.clickRemove = clickRemove;
        }
    };

    function clickAdd (photo) {
        cartService.addToCart(photo).then(function(res) {
            if(res.success)
                apiToken.setUser(res.data);            }
        );
    }

    function clickRemove (photo) {
        cartService.removeToCart(photo).then(function(res) {
            if(res.success)
                apiToken.setUser(res.data);
        })
    }

};

module.exports = addToCart;