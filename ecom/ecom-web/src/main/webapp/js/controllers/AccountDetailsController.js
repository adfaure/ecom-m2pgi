var angular = require('angular');

var templates = {
    'details'   : './js/templates/accountDetails/details.html',
    'upgrade'   : './js/templates/accountDetails/upgrade.html',
    'addPhoto'  : './js/templates/accountDetails/addPhoto.html',
    'stats'     : './js/templates/accountDetails/adminStats.html',
    'adminNav'  : './js/templates/accountDetails/AdminNavBar.html',
    'myCart'    : './js/Cart/detailsCart/CartDetails.html',
    'sellerNav' : './js/templates/accountDetails/SellerNavBar.html',
    'memberNav' : './js/templates/accountDetails/MemberNavBar.html'
};

var accountDetails = function($scope,$routeParams, $location, apiToken) {

    $scope.templates = templates;
    
    $scope.subview   = "details";
    $scope.setView   = setView;

    if($routeParams.subview) {
        $scope.subview = $routeParams.subview;
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
        $scope.subview = view;
    }
};

module.exports = accountDetails;