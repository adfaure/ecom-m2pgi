/**
 * Created by smile on 12/12/15.
 */

var angular = require('angular');

module.exports = function($timeout) {
    return {
        restrict: 'A',
        link : function($scope, $element) {
            $timeout(function() {
                $element[0].focus();
            });
        }
    }
};