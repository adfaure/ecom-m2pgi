var angular = require('angular');

module.exports = function($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            console.log("link");
            var model = $parse(attrs.ecomInputFile);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    }
};