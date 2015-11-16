var angular = require('angular');

module.exports = function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function() {
            	if(element.attr("class") == "") {
                    element.addClass(attrs.toggleClass);
                } else {
                    element.removeClass("active");
                }
            });
        }
    };
};