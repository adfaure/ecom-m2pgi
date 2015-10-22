var angular = require('angular');

var InscriptionController = function($scope, memberService) {

    $scope.user = {
        login : "",
        email : "",
        firstName : "",
        lastName : "",
        password : "",
        accountType : 'N'
    };

    $scope.submit = function() {
        console.log($scope.user);
        memberService.Create($scope.user).then(function (res) {
            console.log(res);
        });
    }


};

module.exports = InscriptionController;