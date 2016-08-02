var accountControllers = angular.module('accountControllers', []);

accountControllers.controller('ListAccountsCtrl', [ '$scope', '$http',
		function($scope, $http) {
			$scope.tempAccount = new Object();
			$scope.dummy = new Object();
			$scope.listAccounts = function() {
				$http.get("/rest/accounts").then(function(response) {
					$scope.accounts = response.data;
				});
			};
			$scope.listAccounts();
			$scope.createAccount = function(isValid) {
				if (isValid) {
					$http.post('/rest/accounts/new', {
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
			$scope.updateAccount = function(account, isInvalid) {
				if (!isInvalid) {
					
					$http.put('/rest/accounts/update/' + account.id, {
						"firstName" : account.firstName,
						"lastName" : account.lastName,
						"email" : account.email,
						"dateOfBirth" : account.dateOfBirth
					}).success(function(data, status, headers, config) {
					
					}).error(function(data, status, headers, config) {
						console.log('error: data = ', data);
					});
				}
			};
			$scope.updateField = function(id, field, newValue) {

				$http.patch('/rest/accounts/update' + '/' + id + '/' + field, {
					"newValue" : newValue
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
					console.log('Error: ', response);
				});
			};
			$scope.updateFieldIfValid = function(id, field, newValue, isValid) {
				if (isValid) {
					$scope.updateField(id, field, newValue);
				}
			};

			$scope.clearModel = function() {
				$scope.id = '';
				$scope.firstName = '';
				$scope.lastName = '';
				$scope.email = '';
				$scope.dateOfBirth = moment();
				
				$scope.newAccFNameForm.newAccountFNameInput.$setPristine();
				$scope.newAccLNameForm.newAccountLNameInput.$setPristine();
				$scope.newAccEmailForm.newAccountEmailInput.$setPristine();
				
			};
			
			$scope.saveTempModel = function(account) {
				$scope.tempAccount = angular.copy(account);
			}
			
			$scope.resetModel = function(account) {
				account.firstName = $scope.tempAccount.firstName;
				account.lastName = $scope.tempAccount.lastName;
				account.email = $scope.tempAccount.email;
				account.dateOfBirth = $scope.tempAccount.dateOfBirth;
			};

		} ]);