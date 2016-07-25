var accountDirectives = angular.module('accountDirectives', []);

accountDirectives.directive('dateFilter', function() {
	  return {
		    require: 'ngModel',
		    link: function(scope, element, attrs, ngModelController) {
		      ngModelController.$parsers.push(function(data) {
		    	return moment(data, 'DD/MM/YYYY').toDate(); 
		      });

		      ngModelController.$formatters.push(function(data) {
		    	 return moment(data).format('DD/MM/YYYY');
		      });
		    }
		  }
		});

accountDirectives.directive('dateValidate', function() {
    return {
        require: 'ngModel',
        link: function(scope, elem, attr, ctrl) {
            
            ctrl.$parsers.push(function(value) {
            	var valid = moment(value, 'DD/MM/YYYY', true).isValid() || value == "";
                ctrl.$setValidity('dateValidate', valid);
                return valid ? value : undefined;
            });
            
            ctrl.$formatters.push(function(value) {
            	var valid = moment(value, 'DD/MM/YYYY', true).isValid() || value == "";
                ctrl.$setValidity('dateValidate', valid);
                return value;
            });
        }
    };
});