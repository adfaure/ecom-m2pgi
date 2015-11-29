var angular = require('angular');

publicPhoto.$inject = ['$http'];

function publicPhoto($http) {
    var service = {};

    service.GetById = GetById;
    service.GetAll = GetAll;
    service.GetAllSortByDate = GetAllSortByDate;
    service.GetAllSortByDateDesc = GetAllSortByDateDesc;
    service.GetAllSortByPrice = GetAllSortByPrice;
    service.GetAllSortByPriceDesc = GetAllSortByPriceDesc;
    service.GetAllSortByViews = GetAllSortByViews;
    service.GetAllSortByLikes = GetAllSortByLikes;
    service.GetPhotoCount = GetPhotoCount;
    service.Search = Search;
    service.GetUserPhotos = GetUserPhotos;
    service.GetUserWishedPhotos = GetUserWishedPhotos;
    service.GetUserWishedPhotosById = GetUserWishedPhotosById;
    service.AddPhotoToWishList = AddPhotoToWishList;
    service.AddPhotoToLikeList = AddPhotoToLikeList;
    service.RemovePhotoFromWishList = RemovePhotoFromWishList;
    service.DeletePhotoById = DeletePhotoById;
    service.Update = Update;
    service.GetUserPhotosWithId = GetUserPhotosWithId;

    return service;

    function GetById(id) {
      return $http.get('api/photos/id/' + id).then(handleSuccess, handleError('Error getting photo by id'));
    }

    function GetAll() {
      return $http.get('api/photos/').then(handleSuccess, handleError('Error getting all photos'));
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

    function AddPhotoToWishList(photoID, memberID) {
      return $http.post('api/photos/wish/' + photoID + '/' + memberID).then(handleSuccess, handleError('Error when wishing photo'));
    }

    function AddPhotoToLikeList(photoID, memberID) {
      return $http.post('api/photos/like/' + photoID + '/' + memberID).then(handleSuccess, handleError('Error when liking photo'));
    }

    function RemovePhotoFromWishList(photoID, memberID) {
      return $http.post('api/photos/unwish/' + photoID + '/' + memberID).then(handleSuccess, handleError('Error when unwishing photo'));
    }

    function DeletePhotoById(id) {
      return $http.delete('api/photos/delete/' + id).then(handleSuccess, handleError('Error when deleting photo by id'));
    }

    function Update(photo) {
    	return $http.put('api/photos/update', photo).then(handleSuccess, handleError('Error updating photo'));
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
