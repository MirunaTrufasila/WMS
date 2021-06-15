angular.module('lite').factory('adminService',
    ['$http', '$q', function ($http, $q) {

        return {

            getApplicationProperties: function () {
                return $http({
                    url: 'api/v1/admin/properties',
                    method: 'GET'
                })
            },

            autocomplete: function (searchType, searchTerm) {
                return $http({
                    url: 'api/v1/admin/autocomplete',
                    method: 'GET',
                    params: {
                        searchType: searchType,
                        searchTerm: searchTerm
                    }
                })
            },


        };
    }]);