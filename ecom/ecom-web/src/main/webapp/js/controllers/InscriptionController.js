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
    
    $scope.submitInscription = function () {
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
					console.log("resultatInscription1 >>>> "+res.success);
		            if (res.success == false) {
						alertService.add("alert-danger", " Erreur, lors de l'inscription ", 1000);
						return false;
		            } else {
						alertService.add("alert-success", "Enregistré ! ", 2000);
						return authentificationService.login($scope.user.login, $scope.user.password);
		            }
	        	}).then(function(res) {
					console.log("resultat2 >>>> "+res.success);
                    if(res.success) {
                        $location.path("/accueil");
                    } else {
						$location.path("/inscription");
                    }
                });
        }
    };

	$scope.logInto = function() {
		authentificationService.login($scope.login, $scope.password).then(
				function(res) {
					console.log("resultatLog >>>> "+res.success);
					if(res.success) {
						$location.path("/");
					} else {
						console.log("path >>>> inscription");
						$location.path("/inscription");
					}
				}
		);
	}
};

module.exports = InscriptionController;