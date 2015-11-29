var angular = require('angular');

var InscriptionController = function ($scope, memberService, sellerService, $location, authentificationService, alertService) {

	$scope.sellerTemplate = './js/templates/sellerInscription.html';

    $scope.user = {
        email: "",
        firstName: "",
        lastName: "",
        accountType: 'N',
		sellerInfo : {
			rib : ""
		}
    };

    $scope.sellerCheckBox = false;
    
    $scope.submit = function () {
    	var res = null;
    	
    	if($scope.sellerCheckBox) {
    		res = sellerService.Create($scope.user);
    		$scope.user.accountType = 'S';
    	} else if(!$scope.sellerCheckBox) {
    		res = memberService.Create($scope.user);
    		$scope.user.accountType = 'M';
    		delete $scope.user.sellerInfo;
    	}
    	
    	if(res != null) {
	    		res.then(function (res) {
		            if (res.success == false) {
						alertService.add("alert-danger", " Erreur, lors de l'inscription ", 1000);
		            } else {

						alertService.add("alert-success", "Enregistré ! ", 2000);
                        authentificationService.login($scope.user.login, $scope.user.password).then(
                            function(res) {
                                if(res.success) {
                                    $location.path("#/accueil");
                                } else {
                                }
                            }
                        );
		            }
	        });
        }
    };
};

module.exports = InscriptionController;