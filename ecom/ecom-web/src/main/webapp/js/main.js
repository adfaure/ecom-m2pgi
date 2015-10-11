require.config({
	baseUrl : 'js',
	paths: {
		angular: './bower_components/angular/angular'
	},
	shim: {
		angular: {
			exports: 'angular'
		}
	},
	deps: ['./app/app']
});
