var angular = require('angular');


var upload = function($scope, $http,$q ,uploadPhoto ,TagsService , alertService) {
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
                    alertService.add("alert-success", "Photo chargée avec succès !", 2000);
                }else{
                    alertService.add("alert-danger", " Erreur lors du chargement de la photo !", 1000);
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
