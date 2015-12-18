var angular = require('angular');

var accueilController = function($scope, $location, $routeParams, $filter, localService, apiToken, publicPhoto) {

  $scope.photos = [];// les photos photos à afficher après la pagination et les filtres.
  var cachedPhotosAll = [];// Les photos recupérer de getALL
  var cachedPhotosSearch = [];// Les photos recupérer de getALL
  $scope.photosPerLoad = 20;
  $scope.canLoadMore = false;
  $scope.welcome = true;

  if(localService.get('welcome') !== null) {
    $scope.welcome = false;
  } else {
    localService.set('welcome', true);
  }

  $scope.search = {
    active : false,
    searching : false,
    terms : '',
    lastTerms : ''
  };

  if($routeParams.terms) {
    $scope.search.lastTerms = $scope.search.terms = $routeParams.terms;
    $scope.search.searching = true;
    publicPhoto.Search($scope.search.terms).then(function(res) {
      cachedPhotosSearch = res;
      $scope.canLoadMore = (cachedPhotosSearch.length > $scope.photosPerLoad);
      $scope.loadmore();//Load from search
      $scope.search.searching = false;
    });
  } else {
    publicPhoto.GetAll().then(function(res) {
      cachedPhotosAll = res;
      $scope.canLoadMore = (cachedPhotosAll.length > $scope.photosPerLoad);
      $scope.loadmore();//Load from get all
    });
  }

  var user;

  if(apiToken.isAuthentificated()) {
    user = apiToken.getUser();
    $scope.welcome = false;
  }

  $scope.register = function() {
    $location.path("/inscription");
  }

  $scope.hideWelcome = function() {
    $scope.welcome = false;
  }

  $scope.elasticsearch = function() {
    if($scope.search.terms) {
      $scope.search.lastTerms = $scope.search.terms;
      $scope.search.searching = true;
      publicPhoto.Search($scope.search.terms).then(function(res) {
        cachedPhotosSearch = res;
        $scope.photos = [];
        $scope.canLoadMore = (cachedPhotosSearch.length > $scope.photosPerLoad);
        $scope.loadmore();//Load from search
        $scope.search.searching = false;
      });
    }
  };

  $scope.reset = function() {
      if(!$scope.search.terms) {
        $scope.search.lastTerms = '';
        if(cachedPhotosAll.length > 0) {
          $scope.photos = cachedPhotosAll;
        } else {
          publicPhoto.GetAll().then(function(res) {
            cachedPhotosAll = res;
            $scope.photos = [];
            $scope.canLoadMore = (cachedPhotosAll.length > $scope.photosPerLoad);
            $scope.loadmore();//Load from get all
          });
        }
      }
  };

  $scope.sort = function(order) {
    if(order == 'date') {
      $scope.photos = $filter('orderBy')($scope.photos, 'dateCreated', true);
    } else if(order == 'price') {
      $scope.photos = $filter('orderBy')($scope.photos, 'price');
    } else if(order == 'priceDesc') {
      $scope.photos = $filter('orderBy')($scope.photos, 'price', true);
    } else if(order == 'views') {
      $scope.photos = $filter('orderBy')($scope.photos, 'views', true);
    } else if(order == 'likes') {
      $scope.photos = $filter('orderBy')($scope.photos, 'likes', true);
    }
  }

  $scope.loadmore = function() {
    var showedLength = $scope.photos.length;
    if($scope.search.lastTerms) {
      var cachedLength = cachedPhotosSearch.length;
      if(showedLength < cachedLength) {
        $scope.photos = $scope.photos.concat(cachedPhotosSearch.slice(showedLength, showedLength + $scope.photosPerLoad));
        if($scope.photos.length == cachedLength) $scope.canLoadMore = false;
      }
    } else {
      var cachedLength = cachedPhotosAll.length;
      if(showedLength < cachedLength) {
        $scope.photos = $scope.photos.concat(cachedPhotosAll.slice(showedLength, showedLength + $scope.photosPerLoad));
        if($scope.photos.length == cachedLength) $scope.canLoadMore = false;
      }
    }
  }
};

module.exports = accueilController;
