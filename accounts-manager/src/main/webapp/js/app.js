var accountApp = angular.module('accountApp',
		[ 'ngRoute', 'ngCookies', 'accountControllers', 'accountDirectives' ])


accountApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'components/listAccounts.html',
		controller : 'ListAccountsCtrl'
	}).otherwise({
		redirectTo : '/'
	});
} ]);