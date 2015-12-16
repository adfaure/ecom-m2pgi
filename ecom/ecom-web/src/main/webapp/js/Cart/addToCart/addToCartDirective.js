var angular = require('angular');

var addToCart = function ($compile, $location, apiToken, cartService) {
    return {
        //E -> just
        restrict: 'E',
        scope: {
            photo: "=photo",
            isButton: "="
        },
        templateUrl: './js/Cart/addToCart/addToCartTemplate.html',
        controller: function ($scope) {
            var user;
            $scope.$watchCollection(cartService.getCart, function (cart) {
                    $scope.alreadyInCart = (cart.find(function (elem, idx, array) {
                        return (elem.photoID == $scope.photo.photoID);
                    }) != undefined);
                    if (!apiToken.isAuthentificated()) {

                    } else {
                        user = apiToken.getUser();
                        if (user.memberID == $scope.photo.sellerID) {
                            $scope.owned = true;
                        } else {
                            $scope.owned = false;
                        }
                    }
                }
            );

            $scope.clicked = function () {
                if (!$scope.photo.isBought) {
                    if ($scope.owned) {
                        $scope.goToMyPhoto();
                    } else if ($scope.alreadyInCart) {
                        clickRemove($scope.photo);
                    } else {
                        clickAdd($scope.photo);
                    }
                } else {
                    $location.path('/photos/details/' + $scope.photo.photoID);
                }
            };

            $scope.clickAdd = clickAdd;
            $scope.clickRemove = clickRemove;
            $scope.goToMyPhoto = function () {
                $location.path('/profil/managePhotos').search({
                    photo: JSON.stringify($scope.photo)
                });
            }
        }
    };

    function clickAdd(photo) {
        cartService.addToCart(photo);
    }

    function clickRemove(photo) {
        cartService.removeToCart(photo);
    }

};

module.exports = addToCart;