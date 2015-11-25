

var controller = function($scope,$location ,alertService, publicPhoto, apiToken) {

    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-danger", " Vous devez vous connecter ou posseder un compte vendeur pour acceder à cette page");
        $location.path("/");
        return;
    }
    var photos = [];
    var user   = apiToken.getUser();

    $scope.loading   = true;
    $scope.showChart = false;

    var chart = $scope.chart = {
        labels : [],
        series : ['sales', 'likes', 'views'],
        data : []
    };

    publicPhoto.GetUserPhotos(user.login).then(function(res) {
        photos = $scope.photos = res;
        photos.forEach(function(photo) {
            photo.selected = true;
        });
        $scope.updateChart();
        $scope.showChart  = true;
        $scope.loading = false;
    });


    $scope.updateChart  = function() {

        var selectedPhotos = photos.filter(function(elem) { return elem.selected });

        chart.labels = selectedPhotos.map(function(elem) {
            return elem.name;
        });

        chart.data[0] = selectedPhotos.map(function(elem) {
            return elem.sales;
        });
        chart.data[1] = selectedPhotos.map(function(elem) {
            return elem.likes;
        });
        chart.data[2] = selectedPhotos.map(function(elem) {
            return elem.views;
        });

        $scope.showChart = (chart.labels.length > 0);
        if(!$scope.showChart) alertService.add("alert-info", " Vous n'avez aucune photo de selectionné ");

    }
};

module.exports = controller;