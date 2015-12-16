var angular = require('angular');

var addToCart = function ($compile, $location, apiToken, cartService) {
    return {
        //E -> just
        restrict: 'E',
        scope: {
            photo: "=photo",
            isButton : "="
        },
        templateUrl: './js/Cart/addToCart/addToCartTemplate.html',
        controller: function ($scope) {
            var user;

            $scope.isAdmin = false;

            $scope.$watch(apiToken.isAuthentificated, function(isAuth) {
                    if(isAuth) {
                        $scope.$watch(apiToken.getUser, function(user) {
                            $scope.isAdmin  = (user && user.accountType == "A");
                        });
                    }
                }
            );

            if (apiToken.isAuthentificated()) {
                var updateCart = $scope.$watch(apiToken.getUser, function () {
                        if (!apiToken.isAuthentificated()) {
                            updateCart(); //stop the watch if the user is not logged in anymore
                            return;
                        }
                        user = apiToken.getUser();
                        if (user.memberID == $scope.photo.sellerID) {
                            $scope.owned = true;
                        } else {
                            $scope.owned = false;
                            $scope.alreadyInCart = (user.cart.find(function (elem, idx, array) {
                                return (elem.photoID == $scope.photo.photoID);
                            }) != undefined);
                        }
                    }
                );
            }

            $scope.clicked = function() {
                if(!$scope.photo.isBought && !$scope.isAdmin) {
                    if ($scope.owned) {
                        $scope.goToMyPhoto();
                    } else if ($scope.alreadyInCart) {
                        clickRemove($scope.photo);
                    } else {
                        clickAdd($scope.photo);
                    }
                } else  {
                    $location.path('/photos/details/' + $scope.photo.photoID);
                }
            };

            $scope.clickAdd    = clickAdd;
            $scope.clickRemove = clickRemove;
            $scope.goToMyPhoto = function () {
                $location.path('/profil/managePhotos').search({
                    photo: JSON.stringify($scope.photo)
                });
            }
        }
    };

    function clickAdd(photo) {
        if(!apiToken.isAuthentificated()) {
            var currentPath = $location.path();
            $location.path('/inscription').search('redirect', currentPath);
        } else {
            cartService.addToCart(photo).then(function (res) {
                    if (res.success)
                        apiToken.setUser(res.data);
                }
            );
        }
    }

    function clickRemove(photo) {
        cartService.removeToCart(photo).then(function (res) {
            if (res.success)
                apiToken.setUser(res.data);
        })
    }

};

module.exports = addToCart;