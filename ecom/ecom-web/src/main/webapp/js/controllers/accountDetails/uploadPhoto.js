var angular = require('angular');


var upload = function($scope, $sce, $http, $q ,uploadPhoto ,TagsService , alertService) {
    $scope.submit = submit;
    $scope.photoData = {
      description: '',
      tags: '',
      price: 1
    };
    $scope.inputTags = [];
    if($scope.user.accountType == 'M') {
        $scope.subview = 'details';
    }

    function submit(redirect) {

        $scope.photoData.tags = $scope.inputTags.map(function(currentValue) {
            return currentValue.name;
        }).join(" ");

        console.log($scope.photoData.tags );

        uploadPhoto.uploadFileToUrl({
            file : $scope.uploadPhoto,
            data : $scope.photoData
        }, $scope.user.memberID).then(
            function(res) {
                if(res.success) {
                    $scope.setView(redirect);
                    alertService.add("alert-success", $sce.trustAsHtml("<strong>Photo chargée avec succès !</strong>"), 2000);
                }else{
                    alertService.add("alert-danger", $sce.trustAsHtml("<strong>Erreur lors du chargement de la photo !</strong>"), 1000);
                }
            }
        );
    }

    var autoComp = new  TagsService.autoComplete();
    $scope.load = function(query) {
        return autoComp.get(query);
    }

};

module.exports = upload;
