var angular = require('angular');

var InscriptionController = function ($scope, memberService, sellerService) {

	$scope.sellerTemplate = './js/templates/sellerInscription.html';
	$scope.alertTemplate  = './js/templates/alertTemplate.html'
	
    $scope.user = {
        email: "",
        firstName: "",
        lastName: "",
        accountType: 'N'
    };
    
    $scope.alert = false;
    $scope.alertType = "";
    $scope.sellerCheckBox = false;
    
    $scope.submit = function () {
    	var res = null;
    	
    	if($scope.sellerCheckBox) {
    		res = sellerService.Create($scope.user);
    		$scope.user.accountType = 'S';
    	} else if(!$scope.sellerCheckBox) {
    		res = memberService.Create($scope.user);
    		$scope.user.accountType = 'N';
    		delete $scope.user.RIB;
    	}
    	
    	if(res != null) {
	    		res.then(function (res) {
		            console.log(res);
		            if (res.success == false) {
		                $scope.alertClass = "alert-danger";
		                $scope.message = " Erreur, lors de l'inscription ";
		                $scope.alert = true;
		            } else {
		            	$scope.alertClass = "alert-success";
		                $scope.message = " Enregistré ! ";
		                $scope.alert = true;
		            }
	        });
        }
    };
    
    $scope.toogleAlert = function() {
    	$scope.alert = !$scope.alert;
    }
    
};

module.exports = InscriptionController;