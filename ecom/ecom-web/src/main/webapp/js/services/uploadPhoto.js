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

            return $http.post(uploadURL, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then( handleSuccess, handleError );
        }
    };

    // private functions

    function handleSuccess(res) {
         return {success: true, data: res};
    }

    function handleError(error) {
        return { success : false , data : res };
    }
};

module.exports = photoService;