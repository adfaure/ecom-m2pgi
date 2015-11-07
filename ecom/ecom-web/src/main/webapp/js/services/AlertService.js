var angular = require('angular');

module.exports = function alertService($interval) {
    var service = {
            add: add,
            closeAlert: closeAlert,
            closeAlertIdx: closeAlertIdx,
            clear: clear,
            get: get
        };
    var alerts = [];
    return service;

    function add(type, msg, timer) {

        console.log("alert added");
        var alert = {
            type: type,
            msg: msg,
            close: function() {
                return closeAlert(this);
            }
        };
        if(timer) {
            $interval(alert.close, timer, 1);
        }
        return alerts.push(alert);
    }

    function closeAlert(alert) {
        return closeAlertIdx(alerts.indexOf(alert));
    }

    function closeAlertIdx(index) {
        return alerts.splice(index, 1);
    }

    function clear(){
        alerts = [];
    }

    function get() {
        return alerts;
    }
};

