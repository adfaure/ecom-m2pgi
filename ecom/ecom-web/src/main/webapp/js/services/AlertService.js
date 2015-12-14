var angular = require('angular');

module.exports = function alertService($interval) {
    var id = 0;
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

        for(var i=0; i<alerts.length; i++)
            alerts[i].show = false;

        var alert = {
            type  : type,
            msg   : msg,
            id    : id++,
            show : true,
            alive : true,
            close : function() {
                return closeAlert(this);
            }
        };
        if(timer) {
            $interval(alert.close.bind(alert), timer, 1);
        }
        return alerts.push(alert);
    }

    function closeAlert(alert) {
        if(alert.alive) {
            alert.alive = false;
            return closeAlertIdx(alerts.indexOf(alert));
        }
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

