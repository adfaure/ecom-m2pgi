
var intToMonth = [
    'janvier',
    'fevrier',
    'mars',
    'avril',
    'mai',
    'juin',
    'juillet',
    'aout',
    'septembre',
    'octobre',
    'novembre',
    'decembre'
];

var controller = function($scope, $location, $sce, alertService, publicPhoto, apiToken, sellerService) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour effectuer cette action ...</strong>"), 3000);
        $location.path("/");
        return;
    }
    var photos = [];
    var user   = apiToken.getUser();

    $scope.showChart = false;

    var photoChart = $scope.photoChart = {
        labels : [],
        series : ['sales', 'likes', 'views'],
        data : []
    };

    var salesChart = $scope.salesChart = {
        labels : [],
        series : ["ventes", "somme total(€)"],
        data : [
            [0,0,0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0,0,0]
        ]
    };

    publicPhoto.GetUserPhotos(user.login).then(function(res) {
        photos = $scope.photos = res;
        photos.forEach(function(photo) {
            photo.selected = true;
        });
        $scope.updateChart();
        $scope.showChart  = true;
    });

    sellerService.getOrderBySellerId(user.memberID).then(function(res) {
        $scope.totalNbSales = 0;
        $scope.totalPrice   = 0;
        $scope.orders       = res;

        $scope.orders.forEach(function(elem) {
            elem.dateCreatedAsDate = new Date(elem.dateCreated);
        });

        $scope.salesChart.series = ["ventes", "somme total(€)"];
        $scope.salesChart.labels = intToMonth;

        $scope.orders.forEach(function(elem) {
            elem.photos.forEach(function(photo) {
                $scope.salesChart.data[0][elem.dateCreatedAsDate.getMonth()] += 1;
                $scope.salesChart.data[1][elem.dateCreatedAsDate.getMonth()] += photo.price;
                $scope.totalPrice   += photo.price;
                $scope.totalNbSales += 1;
            });
        });

    });

    $scope.updateChart  = function() {

        var selectedPhotos = photos.filter(function(elem) { return elem.selected });

        photoChart.labels = selectedPhotos.map(function(elem) {
            return elem.name;
        });

        photoChart.data[0] = selectedPhotos.map(function(elem) {
            return elem.sales;
        });
        photoChart.data[1] = selectedPhotos.map(function(elem) {
            return elem.likes;
        });
        photoChart.data[2] = selectedPhotos.map(function(elem) {
            return elem.views;
        });

        $scope.showChart = (photoChart.labels.length > 0);
        if(!$scope.showChart) alertService.add("alert-info",  $sce.trustAsHtml("<strong>Vous n'avez aucune donnée statistique à afficher ! </strong>"), 2000);

    }
};

module.exports = controller;