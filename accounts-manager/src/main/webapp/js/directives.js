var accountDirectives = angular.module('accountDirectives', []);

accountDirectives.directive('dateFilter', function() {
	  return {
		    require: 'ngModel',
		    link: function(scope, element, attrs, ngModelController) {
		      ngModelController.$parsers.push(function(data) {
		    	return moment(data, 'DD/MM/YYYY').toDate(); 
		      });

		      ngModelController.$formatters.push(function(data) {
		    	 return data == null ? '' : moment(data).format('DD/MM/YYYY');
		      });
		    }
		  }
		});

accountDirectives.directive('dateValidate', function() {
    return {
        require: 'ngModel',
        link: function(scope, elem, attr, ctrl) {
            
            ctrl.$parsers.push(function(value) {
            	var formDate = moment(value, 'DD/MM/YYYY', true);
            	var valid = value == '' || (formDate.isValid() && formDate.isBefore(moment()) );
            	ctrl.$setValidity('dateValidate', valid);
                return valid ? value : undefined;
            });
            
            ctrl.$formatters.push(function(value) {
            	var formDate = moment(value, 'DD/MM/YYYY', true);
            	var valid = value == '' || (formDate.isValid() && formDate.isBefore(moment()) );
                ctrl.$setValidity('dateValidate', valid);
                return value;
            });
        }
    };
});

accountDirectives.directive('mandatoryValidate', function() {
    return {
        require: 'ngModel',
        link: function(scope, elem, attr, ctrl) {
            
            ctrl.$parsers.push(function(value) {
            	var valid = value != '';
                ctrl.$setValidity('mandatoryValidate', valid);
                return valid ? value : undefined;
            });
            
            ctrl.$formatters.push(function(value) {
            	var valid = value != '';
                ctrl.$setValidity('mandatoryValidate', valid);
                return value;
            });
        }
    };
});


accountDirectives.directive('nameValidate', function() {
    return {
        require: 'ngModel',
        link: function(scope, elem, attr, ctrl) {
            var regex = /^[a-zA-Z '-]+$/;
            ctrl.$parsers.push(function(value) {
            	if (value != undefined) {
	            	var valid =  regex.test(value);
	                ctrl.$setValidity('nameValidate', valid);
            	}
                return valid ? value : undefined;
            });
            
            ctrl.$formatters.push(function(value) {
            	if (value != undefined) {
            		var valid =  regex.test(value);
	                ctrl.$setValidity('nameValidate', valid);
            	}
                return value;
            });
        }
    };
});

