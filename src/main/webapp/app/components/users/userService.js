angular.module('lite').factory('userService',
    ['$http',
        function ($http) {
            return {

                searchTerm: undefined,

                get: function (pageNumber, pageSize) {
                    return $http({
                        url: 'api/v1/users',
                        method: 'GET',
                        params: {
                            pageNumber: pageNumber,
                            pageSize: pageSize
                        }
                    })
                },

                getUser: function (id) {
                    return $http({
                        url: 'api/v1/users/' + id,
                        method: 'GET'
                    })
                },

                updateUser: function (user) {
                    return $http({
                        url: 'api/v1/users',
                        method: 'PUT',
                        data: user
                    })
                },

                createUser: function (user) {
                    return $http({
                        url: 'api/v1/users',
                        method: 'POST',
                        data: user
                    })
                },

                deleteUser: function (id) {
                    return $http({
                        url: 'api/v1/users/' + id,
                        method: 'DELETE'
                    })
                },

                searchUser: function (searchTerm, pageNumber, pageSize) {
                    this.searchTerm = searchTerm;
                    return $http({
                        url: 'api/v1/users/filter',
                        method: 'GET',
                        params: {
                            filter: 'NAME',
                            value: searchTerm ? searchTerm : "",
                            pageNumber: pageNumber,
                            pageSize: pageSize
                        }
                    })
                }
            }
        }
    ]
);