var angular = require('angular');


function photoService($http) {
    var url = "api/photos/upload/seller/";
    return {
        uploadFileToUrl : function (file, sellerId) {
            var fd = new FormData();
            var uploadURL = url + sellerId;
            console.log(file.data);
            fd.append('file', file.file);

            angular.forEach(file.data, function(val,name) {
                fd.append(name, val);
            });

            $http.post(uploadURL, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                .success(function () {
                })
                .error(function () {
                });
        }
    }
};

module.exports = photoService;