var angular = require('angular');


var upload = function($scope, uploadPhoto, alertService) {
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
                    alertService.add("alert-success", "Photo chargée avec succès !", 2000);
                }else{
                    alertService.add("alert-danger", " Erreur lors du chargement de la photo !", 1000);
                }
            }
        );
    }
};

module.exports = upload;
