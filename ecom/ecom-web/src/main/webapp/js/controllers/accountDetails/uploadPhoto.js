var angular = require('angular');


var upload = function($scope, uploadPhoto) {
    $scope.submit = submit;
    $scope.photoData = {

    };
    if($scope.user.accountType == 'M') {
        $scope.subview = 'details';
    }

    function submit() {
        uploadPhoto.uploadFileToUrl({
            file : $scope.uploadPhoto,
            data : $scope.photoData
        }, $scope.user.memberID).then(
            function(res) {
                console.log(res);
                if(res.success) {
                    $scope.setView('details');
                }
            }
        );
    }
};

module.exports = upload;