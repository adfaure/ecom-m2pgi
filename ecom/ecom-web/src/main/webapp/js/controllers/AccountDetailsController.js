var angular = require('angular');

var templates = {
    'details'   : './js/templates/accountDetails/details.html',
    'upgrade'   : './js/templates/accountDetails/upgrade.html',
    'addPhoto'  : './js/templates/accountDetails/addPhoto.html',
    'managePhotos' : './js/templates/accountDetails/managePhotos.html',
    'stats'     : './js/templates/accountDetails/adminStats.html',
    'adminNav'  : './js/templates/accountDetails/AdminNavBar.html',
    'myCart'    : './js/Cart/detailsCart/CartDetails.html',
    'history'   : './js/Orders/OrdersHistory/ordersHistory.html',
    'sellerNav' : './js/templates/accountDetails/SellerNavBar.html',
    'memberNav' : './js/templates/accountDetails/MemberNavBar.html',
    'myPage'    : './js/SellerPage/ManagePage/managePageTemplate.html'

};

var accountDetails = function($scope, $routeParams, $location, apiToken) {

    $scope.templates = templates;
    $scope.subview   = "details";
    $scope.setView   = setView;

    if($routeParams.section) {
        $scope.subview = $routeParams.section;
    }

    if(!apiToken.isAuthentificated()) {
        $location.path("/");
    } else {
        $scope.user  = apiToken.getUser();
    }

    $scope.$watch(
        apiToken.getUser, function() {
            $scope.user = apiToken.getUser();
        }
    );

    function setView(view) {
        $location.path("/profil/" + view);
    }
};

module.exports = accountDetails;
