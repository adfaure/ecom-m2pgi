var angular = require('angular');


var upload = function($scope, $sce, $http, $q ,$location ,uploadPhoto , apiToken, TagsService , alertService) {
    var user = {};
    if(!apiToken.isAuthentificated()) {
        alertService.add("alert-info", $sce.trustAsHtml("<strong>Vous devez être <a href='#/inscription'>authentifié</a> pour uploader une photo ...</strong>"), 3000);
        //$location.path('/inscription/seller').search('redirect', '/profil/addPhoto');
    } else {
        user = apiToken.getUser();
        if($scope.user.accountType == 'M') {
            alertService.add("alert-info", $sce.trustAsHtml("<strong>Il faut posseder un <a href='#/profil/upgrade'>compte vendeur</a> pour uploader un photo</strong>"), 3000);
            //$location.path('/profil/upgrade').search('redirect', '/profil/addPhoto');
        }
    }

    $scope.loading = false;
    $scope.submit = submit;
    $scope.photoData = {
      description: '',
      tags: '',
      price: 1
    };

    $scope.inputTags = [];



    function submit(redirect) {
        $scope.loading = true;
        $scope.photoData.tags = $scope.inputTags.map(function(currentValue) {
            return currentValue.name;
        }).join(" ");

        uploadPhoto.uploadFileToUrl({
            file : $scope.uploadPhoto,
            data : $scope.photoData
        }, $scope.user.memberID).then(
            function(res) {
                $scope.loading = false;
                if(res.success) {
                    $scope.setView(redirect);
                    alertService.add("alert-success", $sce.trustAsHtml("<strong>Photo chargée avec succès !</strong>"), 2000);
                }else{
                    alertService.add("alert-danger", $sce.trustAsHtml("<strong>Erreur lors du chargement de la photo !</strong>"), 2000);
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
