var angular = require('angular');


var upload = function($scope, $sce, uploadPhoto, alertService) {
    $scope.submit = submit;
    $scope.photoData = {

    };
    if($scope.user.accountType == 'M') {
        $scope.subview = 'details';
    }

    function submit(redirect) {
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
};

module.exports = upload;
