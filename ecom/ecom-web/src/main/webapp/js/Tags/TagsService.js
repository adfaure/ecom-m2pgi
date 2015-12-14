/**
 * Created by dadou on 14/12/15.
 */
var angular = require('angular');

tagService.$inject = ['$http', '$q'];

function tagService($http, $q) {
    var tags = [];
    var service = {};

    service.GetTags = GetTags;
    service.autoComplete = autoComplete;
    return service;

    function GetTags() {
        return $http.get('api/tags/').then(handleSuccess, handleError('Error getting user by id'));
    }

    //attention pour que ca marche, il faut creer un objet d etype autoComplete
    // new TagsService.autoComplete()
    function autoComplete() {

        var that = this;
        this.tags = [];
        var loaded = false;
        this.get = function(query) {

            var promise;
            if(!loaded) {
                promise = $http.get('api/tags/').then(function (res) {
                    that.tags  = res.data;
                    loaded = true;
                    return that.tags;
                });
            } else {
                var defer = $q.defer();
                defer.resolve(that.tags);
                promise = defer.promise;
            }

            return promise.then(function (tags) {
                return tags.filter(function (elem) {
                    return elem.name.startsWith(query)
                });
            });
        }
    }

    function handleSuccess(res) {
        return res.data;
    }

    function handleError(error) {
        return function () {
            return { success: false, message: error };
        };
    }
}

module.exports = tagService;
