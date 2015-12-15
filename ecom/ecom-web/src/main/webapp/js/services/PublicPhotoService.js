var angular = require('angular');

publicPhoto.$inject = ['$http', 'localService', '$q'];

function publicPhoto($http, localService, $q) {
    var service = {};

    service.GetById = GetById;
    service.GetAll = GetAll;
    service.GetTop10 = GetTop10;
    service.GetAllSortByDate = GetAllSortByDate;
    service.GetAllSortByDateDesc = GetAllSortByDateDesc;
    service.GetAllSortByPrice = GetAllSortByPrice;
    service.GetAllSortByPriceDesc = GetAllSortByPriceDesc;
    service.GetAllSortByViews = GetAllSortByViews;
    service.GetAllSortByLikes = GetAllSortByLikes;
    service.GetReportedPhotos = GetReportedPhotos;
    service.GetLastPhotosFromSellers = GetLastPhotosFromSellers;
    service.GetPhotoCount = GetPhotoCount;
    service.Search = Search;
    service.GetUserPhotos = GetUserPhotos;
    service.GetUserWishedPhotos = GetUserWishedPhotos;
    service.GetUserWishedPhotosById = GetUserWishedPhotosById;
    service.AddPhotoToWishList = AddPhotoToWishList;
    service.AddPhotoToLikeList = AddPhotoToLikeList;
    service.RemovePhotoFromWishList = RemovePhotoFromWishList;
    service.RemovePhotoFromLikeList = RemovePhotoFromLikeList;
    service.Flag = Flag;
    service.RemovePhotoFromWishList = RemovePhotoFromWishList;
    service.DeletePhotoById = DeletePhotoById;
    service.DeleteReportedPhoto = DeleteReportedPhoto;
    service.ValidateReportedPhoto = ValidateReportedPhoto;
    service.Update = Update;
    service.GetUserPhotosWithId = GetUserPhotosWithId;
    service.getBoughtPhoto= getBoughtPhoto;
    service.isPhotoBought= isPhotoBought;

    return service;

    function GetById(id) {
      return $http.get('api/photos/id/' + id).then(handleSuccess, handleError('Error getting photo by id'));
    }

    function GetAll() {
      return $http.get('api/photos/').then(handleSuccess, handleError('Error getting all photos'));
    }
    
    function GetTop10(){
    	return $http.get('api/photos/top10').then(handleSuccess, handleError('Error getting top 10 photos'));
    }

    function GetAllSortByDate() {
      return $http.get('api/photos/orderby?criteria=date')
        .then(handleSuccess, handleError('Error getting photos ordered by date'));
    }

    function GetAllSortByDateDesc() {
      return $http.get('api/photos/orderby?criteria=date&order=DESC')
        .then(handleSuccess, handleError('Error getting photos ordered by date'));
    }

    function GetAllSortByPrice() {
      return $http.get('api/photos/orderby?criteria=price')
        .then(handleSuccess, handleError('Error getting photos ordered by price'));
    }

    function GetAllSortByPriceDesc() {
      return $http.get('api/photos/orderby?criteria=price&order=DESC')
        .then(handleSuccess, handleError('Error getting photos ordered by price'));
    }

    function GetAllSortByViews() {
      return $http.get('api/photos/orderby?criteria=views')
        .then(handleSuccess, handleError('Error getting photos ordered by view'));
    }

    function GetAllSortByLikes() {
      return $http.get('api/photos/orderby?criteria=likes')
        .then(handleSuccess, handleError('Error getting photos ordered by likes'));
    }

    function GetReportedPhotos() {
      return $http.get('api/photos/reported/').then(handleSuccess, handleError('Error getting reported photos'));
    }

    function GetPhotoCount() {
        return $http.get('api/photos/count/').then(handleSuccess, handleError('The photo count couldnt be accomplished'));
    }

    function Search(text) {
      return $http.get('api/photos/search/' + text).then(handleSuccess, handleError('Error when searching photos'));
    }

    function GetUserPhotos(login) {
      return $http.get('api/photos/user/login/' + login).then(handleSuccess, handleError('Error when get user photos'));
    }

    function GetUserPhotosWithId(id) {
        return $http.get('api/photos/user/id/' + id).then(handleSuccess, handleError('Error when get user photos'));
    }

    function GetUserWishedPhotos(login) {
      return $http.get('api/photos/user/login/' + login + '/wishes').then(handleSuccess, handleError('Error when getting user wishlist'));
    }

    function GetUserWishedPhotosById(id) {
      return $http.get('api/photos/user/id/' + id + '/wishes').then(handleSuccess, handleError('Error when getting user wishlist'));
    }

    function GetLastPhotosFromSellers(id, numberMax) {
        return $http.get('api/photos/user/id/' + id + '/maxNum/'+numberMax).then(handleSuccess, handleError('Error when getting user wishlist'));
    }
    
    function Flag(photoID, memberID) {
      return $http.post('api/photos/flag/' + photoID + '/' + memberID).then(handleSuccess, handleError('Error when wishing photo'));
    }

    function AddPhotoToWishList(photoID, memberID) {
      return $http.post('api/photos/wish/' + photoID + '/' + memberID).then(handleSuccess, handleError('Error when wishing photo'));
    }

    function AddPhotoToLikeList(photoID, memberID) {
      return $http.post('api/photos/like/' + photoID + '/' + memberID).then(handleSuccess, handleError('Error when liking photo'));
    }

    function RemovePhotoFromWishList(photoID, memberID) {
      return $http.post('api/photos/unwish/' + photoID + '/' + memberID).then(handleSuccess, handleError('Error when unwishing photo'));
    }

    function RemovePhotoFromLikeList(photoID, memberID) {
      return $http.post('api/photos/unlike/' + photoID + '/' + memberID).then(handleSuccess, handleError('Error when unwishing photo'));
    }

    function DeletePhotoById(id) {
      return $http.delete('api/photos/delete/' + id).then(handleSuccess, handleError('Error when deleting photo by id'));
    }

    function DeleteReportedPhoto(id) {
      return $http.delete('api/photos/reported/' + id).then(handleSuccess, handleError('Error when deleting reported photo by id'));
    }

    function ValidateReportedPhoto(id) {
      return $http.post('api/photos/reported/validate/' + id).then(handleSuccess, handleError('Error when validating reported photo'));
    }

    function Update(photo) {
    	return $http.put('api/photos/update', photo).then(handleSuccess, handleError('Error updating photo'));
    }

    function isPhotoBought (photo, memberID) {
        var photos = localService.getObject('boughtPhoto');
        var idx = photos.indexOf(function(elem) {return photo.photoID == elem.photoID});
        if( idx != -1) {
            var defer = $q.defer();
            defer.resolve(photos[idx]);
            return defer.promise.then(handleSuccess);
        }

        return $http.get('api/photos/id/' + photo.photoID + '/user/id/' + memberID + '/isBought').then(function(res) {
            console.log(res);
            return res;
        }).then(handleSuccess)

    }


    function getBoughtPhoto(memberID) {

        return $http.get('api/photos/bought/user/id/'+ memberID).then(function(res) {
            var photos = localService.getObject('boughtPhoto');
            if(!photos) {
                photos = res.data;
            } else {
                photos = photos.concat(res.data.filter(function(elem) {
                    return (photos.indexOf(function(photo) {
                        return elem.photoID == photo.photoID;
                    }) != -1);
                }));
            }
            localService.set('boughtPhoto', JSON.stringify(photos));
            res.data = photos
            return res;
        }, handleError('Erreur')).then(handleSuccess);

    }

    // private functions
    function handleSuccess(res) {
        return res.data;
    }

    function handleError(error) {
        return {success: false, message: error};
    }
};

module.exports = publicPhoto;
