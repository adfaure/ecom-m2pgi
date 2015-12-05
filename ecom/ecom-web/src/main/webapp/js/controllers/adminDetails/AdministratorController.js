var angular = require('angular');

var intToMonth = [
                  'janvier',
                  'fevrier',
                  'mars',
                  'avril',
                  'mai',
                  'juin',
                  'juillet',
                  'aout',
                  'septembre',
                  'octobre',
                  'novembre',
                  'decembre'
                  ];

var administratorController = function($scope,  publicPhoto, orderService, memberService, sellerService) {

	/*Initialization*/
	var salesChart = $scope.salesChart = {
			labels : [],
			series : ["ventes", "somme total(€)"],
			data : [
			        [0,0,0,0,0,0,0,0,0,0,0,0],
			        [0,0,0,0,0,0,0,0,0,0,0,0]
			        ]
	};
	
	$scope.data = {
		    singleSelect: null,
		    availableOptions: [],
		    year : ""
	};
	
	var top10Sellers = $scope.top10Sellers = {
	        labels : [],
	        series : ['ventes', 'photos'],
	        data : []
	 };
	
	var top10Photos = $scope.top10Photos = {
	        labels : [],
	        series : ['ventes'],
	        data : []
	 };


	publicPhoto.GetPhotoCount().then(function(res) {
		$scope.countText = res;
		$scope.photoCount = res;
	});

	orderService.GetOrderCount().then(function(res) {
		$scope.purchaseCount = res.data;
	});

	orderService.GetTotalPurchaseCost().then(function(res) {
		$scope.totalPurchaseCost = res.data;
	});

	memberService.GetCount().then(function(res) {
		$scope.memberCount = res;
	});

	sellerService.GetCount().then(function(res) {
		$scope.sellerCount = res;
	});
	
	publicPhoto.GetTop10().then(function(res) {

		var photos = res;
	
		top10Photos.labels = photos.map(function(elem) {
	            return elem.name;
	    });

		top10Photos.data[0] = photos.map(function(elem) {
	            return elem.sales;
	    });
		
	});
	
	
	sellerService.GetTopSellers().then(function(res){
		var sellers = res;
		/*Initialize the data position zero*/
		top10Sellers.data[0] = [];
		top10Sellers.data[1] = [];
		
		sellers.forEach(function(elem, idx){
				sellerService.getOrderBySellerId(elem.memberID).then(function(res){
						
						var orders = res;
						var salesCounter = 0;
						var photoCounter = 0;
						var arri = [];
						
						orders.forEach(function(elem) {
							salesCounter = salesCounter + elem.photos.length;
							var photos = elem.photos;
						
							photos.forEach(function(p){
								if(arri.indexOf(p.photoID) == -1){
									photoCounter++; 
									arri.push(p.photoID);
								}
							});
				        });
					
						top10Sellers.labels.push(elem.login);
						top10Sellers.data[0].push(salesCounter);
						top10Sellers.data[1].push(photoCounter);			
				});
		});
	});
	
	

	orderService.GetAllOrders().then(function(res) {

		$scope.totalNbSales = 0;
		$scope.totalPrice 	= 0;
		$scope.orders       = res.data;
		
		$scope.orders.forEach(function(elem) {
			elem.dateCreatedAsDate = new Date(elem.dateCreated);
		});

		$scope.salesChart.series = ["ventes", "somme total(€)"];
		$scope.salesChart.labels = intToMonth;

		$scope.orders.forEach(function(elem) {
			if($scope.data.availableOptions.indexOf(elem.dateCreatedAsDate.getFullYear()) == -1){
				$scope.data.availableOptions.push(elem.dateCreatedAsDate.getFullYear());
			}
		});
		
		if($scope.data.availableOptions[0] != null){
			$scope.data.singleSelect = $scope.data.availableOptions[0];
		}
		
		$scope.updateOrders();

	});
	
	$scope.updateOrders = function(){
		
		$scope.data.year = $scope.data.singleSelect;
		
		$scope.totalPrice = 0;
		$scope.totalNbSales = 0;
		
		$scope.salesChart.data = [
		        [0,0,0,0,0,0,0,0,0,0,0,0],
		        [0,0,0,0,0,0,0,0,0,0,0,0]
		        ]
		
		$scope.orders.forEach(function(elem) {
			if(elem.dateCreatedAsDate.getFullYear() == $scope.data.singleSelect){
				elem.photos.forEach(function(photo) {
					$scope.salesChart.data[0][elem.dateCreatedAsDate.getMonth()] += 1;
					$scope.salesChart.data[1][elem.dateCreatedAsDate.getMonth()] += photo.price;
					$scope.totalPrice   += photo.price;
					$scope.totalNbSales += 1;
				});
			}
		});

	};
};


module.exports = administratorController;
