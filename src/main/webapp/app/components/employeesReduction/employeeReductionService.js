angular.module('lite').factory('employeeReductionService',
    ['$http',
        function ($http) {
            return {

                searchTerm: undefined,

                searchEmployeeReduction: function (searchTerm, pageNumber, pageSize) {
                    this.searchTerm = searchTerm;
                    return $http({
                        url: 'api/v1/employees-reduction/filter',
                        method: 'GET',
                        params: {
                            filter: 'NAME',
                            value: searchTerm ? searchTerm : "",
                            pageNumber: pageNumber,
                            pageSize: pageSize
                        }
                    })
                },

                getEmployeesReduction: function (pageNumber, pageSize) {
                    return $http({
                        url: 'api/v1/employees-reduction',
                        method: 'GET',
                        params: {
                            pageNumber: pageNumber,
                            pageSize: pageSize
                        }
                    })
                },

                getEmployeeReduction: function (id) {
                    return $http({
                        url: 'api/v1/employees-reduction/' + id,
                        method: 'GET'
                    })
                },

                uploadFile: function (fileContent, fileName) {
                    return $http({
                        url: 'api/v1/employees-reduction/upload',
                        method: 'POST',
                        data: fileContent,
                        params: {
                            fileName: fileName
                        }
                    })
                }
            }
        }
    ]
);