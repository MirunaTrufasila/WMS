angular.module('lite').factory('userPrivilegesService',
    ['$http', function ($http) {

        return {

            getUser: function (id) {
                return $http({
                    url: 'api/v1/users/' + id,
                    method: 'GET'
                })
            },

            getUserPrivileges: function (userId) {
                return $http({
                    url: 'api/v1/privileges/users/' + userId,
                    method: 'GET'
                })
            },

            update: function (userId, privileges) {
                return $http({
                    url: 'api/v1/privileges/users/' + userId,
                    method: 'POST',
                    data: privileges
                })
            }
        };
    }]);