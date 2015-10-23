var angular = require('angular');

var InscriptionController = function ($scope, memberService) {

    $scope.user = {
        login: "",
        email: "",
        firstName: "",
        lastName: "",
        password: "",
        accountType: 'N'
    };

    $scope.error = "";

    $scope.submit = function () {

        memberService.Create($scope.user).then(function (res) {
            console.log(res);
            if (res.success == false) {
                $scope.error = " Erreur, lors de l'inscription ";
            } else {
                $scope.error = " Enregistré ! ";
            }
        });

    }


};

module.exports = InscriptionController;