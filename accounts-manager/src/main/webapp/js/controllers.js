var accountControllers = angular.module('accountControllers', []);

accountControllers.controller('ListAccountsCtrl', [ '$scope', '$http',
		function($scope, $http) {
			$scope.listAccounts = function() {
				$http.get("/rest/accounts").then(function(response) {
					$scope.accounts = response.data;
				});
			};
			$scope.listAccounts();
			$scope.createAccount = function(isValid) {
				if (isValid) {
					$http.post('/rest/accounts/save', {
						"firstName" : $scope.firstName,
						"lastName" : $scope.lastName,
						"email" : $scope.email,
						"dateOfBirth" : $scope.dateOfBirth
					}).success(function(data, status, headers, config) {
						$scope.clearModel();
						$scope.accounts.push(data);
					}).error(function(data, status, headers, config) {
						console.log('error: data = ', data);
					});
				}

			};
			$scope.updateAccount = function(account) {

				$http.post('/rest/accounts/save', {
					"id" : account.id,
					"firstName" : account.firstName,
					"lastName" : account.lastName,
					"email" : account.email,
					"dateOfBirth" : account.dateOfBirth
				}).error(function(data, status, headers, config) {
					console.log('Error: ', data);
				});

			};
			$scope.deleteAccount = function(id) {
				$http({
					method : 'DELETE',
					url : '/rest/accounts/delete/' + id
				}).then(function successCallback(response) {
					$scope.listAccounts();
				}, function errorCallback(response) {
					console.log('Error: ', response.data);
				});
			};
			$scope.updateAccountIfValid = function(account, isValid) {
				if (isValid) {
					$scope.updateAccount(account);
				}
			};

			$scope.clearModel = function() {
				$scope.id = '';
				$scope.firstName = '';
				$scope.lastName = '';
				$scope.email = '';
				$scope.dateOfBirth = moment();
			};

		} ]);