var angular = require('angular');

module.exports = function($scope, $location, apiToken, cartService) {
    if(!apiToken.isAuthentificated()) {
        $location.path("/");
    }
    $scope.totalPrice = 0;
    var user = apiToken.getUser();

    var updateCart = $scope.$watch(apiToken.getUser, function() {
            if(!apiToken.isAuthentificated()) {
                updateCart(); //stop the watch if the user is not logged in anymore
                return;
            }
            user = apiToken.getUser();
            $scope.cart = user.cart;
            $scope.totalPrice = 0;
            if($scope.cart.length > 0) {

                user.cart.forEach(function(elem){
                    $scope.totalPrice+=elem.price;
                });
            }
        }
    );

    $scope.clearCart = function() {
        cartService.clearCart().then(function(res) {
            if(res.success)
                apiToken.setUser(res.data);

        });
    }

};
