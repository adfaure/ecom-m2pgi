var angular = require('angular');


function photoService($http, properties) {
    return {
        uploadFileToUrl: function (file, sellerId) {
            return properties.get("upload_url").then(function (url) {
                var fd = new FormData();
                var uploadURL = url + "/"  + sellerId;
                fd.append('photo', file.file);

                angular.forEach(file.data, function (val, name) {
                    fd.append(name, val);
                });

                return $http.post(uploadURL, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                });
            }).then(handleSuccess, handleError);
        }
    };

    // private functions
    function handleSuccess(res) {
        return {success: true, data: res};
    }

    function handleError(error) {
        return { success: false, data: error};
    }
}


module.exports = photoService;