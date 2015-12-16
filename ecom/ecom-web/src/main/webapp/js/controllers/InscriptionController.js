var angular = require('angular');

var InscriptionController = function ($scope, $sce, $routeParams, apiToken, memberService, sellerService, $location, authentificationService, alertService) {

	if(apiToken.isAuthentificated()) {
		$location.path('/');
	};

	$scope.sellerTemplate = './js/templates/sellerInscription.html';

	$scope.user = {
		email: "",
		firstName: "",
		lastName: "",
		accountType: 'N',
		password : "",
		sellerInfo : {
			rib : ""
		}
	};

	$scope.sellerCheckBox  = $routeParams.type == 'seller';
	$scope.existingLogin   = false;
	$scope.existingEmail   = false;
	$scope.checkPass       = {
		valide : true,
		message : ""
	};

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
				if (res.success == false) {
					alertService.add("alert-danger", $sce.trustAsHtml("<strong>Erreur, lors de l'inscription</strong>"), 1000);
					return false;
				} else {
					alertService.add("alert-success", $sce.trustAsHtml("<strong>Enregistré !</strong>"), 2000);
					return authentificationService.login($scope.user.login, $scope.user.password);
				}
			}).then(function(res) {
				if(res.success) {
					if($routeParams.redirect) {
						var payLoad = $routeParams.payLoad || JSON.stringify({});
						$location.path($routeParams.redirect).search('payLoad', payLoad);
					} else {
						if($scope.user.accountType == 'S') {
							$location.path("/profil/myPage").search('first');
						} else {
							$location.path("/accueil");
						}
					}
				} else {
					$location.path("/inscription");
				}
			});
		}
	};

	$scope.logInto = function() {
		authentificationService.login($scope.login, $scope.password).then(
			function(res) {
				if(res.success) {
					if($routeParams.redirect) {
						var payLoad = $routeParams.payLoad || JSON.stringify({});
						$location.path($routeParams.redirect).search('payLoad', payLoad);
					} else {
						$location.path("/accueil");
					}
				} else {
					alertService.add("alert-danger", $sce.trustAsHtml("<strong>Le login entré ne correspond à aucun compte</strong>"), 5000);
					$location.path("/inscription");
				}
			}
		);
	};

	/*$scope.checkPassword = function() {
		if(!$scope.user.password) {
			$scope.checkPass.valide = false;
			$scope.checkPass.message = "Le champs mot de passe est obligatoire !";
		} else if($scope.user.password.length <= 4) {
			$scope.checkPass.valide = false;
			$scope.checkPass.message = "Le mot de passe est très court !";
		} else {
			$scope.checkPass.valide = true
		}
	};*/

	$scope.checkLogin = function() {
		if($scope.user.login) {
			console.log($scope.user.login);
			memberService.IsExisting($scope.user.login).then(function(res) {
				if(res) {
					$scope.existingLogin = true;
					$scope.inscriptionform.loginInput.$setValidity("inscription login", false);
				} else {
					$scope.existingLogin = false;
					$scope.inscriptionform.loginInput.$setValidity("inscription login", true);
				}
				return $scope.existingLogin;
			});
		}
	}

	$scope.checkEmail = function() {
		if($scope.user.email) {
			console.log($scope.user.email);
			memberService.IsExistingByEmail($scope.user.email).then(function(res) {
				if(res) {
					$scope.existingEmail = true;
					$scope.inscriptionform.emailInput.$setValidity("inscription email", false);
				} else {
					$scope.existingEmail = false;
					$scope.inscriptionform.emailInput.$setValidity("inscription email", true);
				}
				return $scope.existingEmail;
			});
		}
	}
};

module.exports = InscriptionController;
