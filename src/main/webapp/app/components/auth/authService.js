angular.module('lite').factory('authService',
    ['$http', '$q', '$rootScope', function ($http, $q, $rootScope) {

        return {

            getUserResource: function () {
                if ($rootScope.userResource) { // if possible, use the cached permissions
                    var deferred = $q.defer();
                    var response = {data: $rootScope.userResource};
                    deferred.resolve(response);
                    return deferred.promise;
                }
                return $http({ // load all current user permissions from the database
                    url: 'auth/current',
                    method: 'GET'
                })
            },

            hasRole: function (role) {
                let checkRole = role;
                if (role && !role.toUpperCase().startsWith('ROLE')) {
                    checkRole = 'ROLE_' + role;
                }
                return this.hasAuthority(checkRole);
            },

            hasAuthority: function (requiredPermissions) {
                if (!requiredPermissions || requiredPermissions === "-1") { // some views might not require permissions => resolve as true
                    return true;
                }
                return this.getUserResource()
                    .then(function (response) {
                        if (response.data != null) {
                            let checkPermissionList;
                            if (Array.isArray(requiredPermissions)) {
                                checkPermissionList = requiredPermissions;
                            } else {
                                checkPermissionList = requiredPermissions.split(",");
                            }
                            const currentUserPermissions = $rootScope.userResource.permissions;
                            for (const checkPermission of checkPermissionList) {
                                if (currentUserPermissions.find(permission =>
                                    typeof checkPermission === 'string' && permission === checkPermission.trim())) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }, function (errResponse) {
                        console.error(errResponse);
                        return false;
                    });
            },

            logout: function () {
                delete $rootScope.userResource;
                $http({
                    url: 'logout',
                    method: 'POST'
                });
            }
        }
    }
    ]);