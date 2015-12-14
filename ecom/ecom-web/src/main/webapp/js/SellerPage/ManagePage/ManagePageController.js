var angular = require('angular');

var pageTemplate = "./js/SellerPage/Page/PageTemplate.html";

var controller = function($scope, $sce, pageService, alertService, apiToken, publicPhoto) {
    var user = {};
    $scope.pageTemplate = pageTemplate;
    $scope.user    = user = apiToken.getUser();
    $scope.modeView     = true;
    $scope.submit       = submit;
    $scope.toogleMode   = toogleMode;

    pageService.getPage($scope.user.memberID).then(function (res) {
        $scope.page = page = res.data;
        publicPhoto.GetUserPhotosWithId($scope.user.memberID).then(
            function (res) {
                $scope.photos = res;
            }
        );
    });


    function submit() {
        pageService.updatePage(user.memberID, $scope.page).then(function(res) {
            if(res.success) {
                $scope.page = res.data;
                alertService.add("alert-info", $sce.trustAsHtml("<strong>Votre page a bien été mise à jour ! </strong>"), 1000);
                toogleMode();
            }
        })
    }

    function toogleMode() {
        $scope.modeView = !$scope.modeView;
    }

};

module.exports = controller;
