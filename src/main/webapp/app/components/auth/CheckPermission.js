/**
 * Directive used to disable UI components, such as buttons based on specific user rights.
 * The disable behavior is the default one, but can be overriden by 'redirect', in which case the current path
 * will be redirected to /access-denied. However, this should probably be configured at routing level instead.
 * Calling this directive as:
 *
 *          <check-permission permission="555" behavior="disable"></check-permission>
 *
 * is a mystake, as the disable will not work. Instead, on UI level, add:
 *
 *          check-permission="555"
 *
 * to effectively disable that component, if the user has no right to access that.
 */
angular.module('lite').directive('checkPermission', ['$location', 'authService', '$q', '$timeout',
    function ($location, authService, $q, $timeout) {

        return {
            restrict: 'EA', // can be used as (E)lement or element (A)ttribute

            scope: {
                behavior: '@', // possible values are: disable (default) or hide
                permission: '@'
            },

            link: function (scope, element, attrs) {
                scope.permissions = scope.permission ? scope.permission : attrs.checkPermission;
                if (!scope.permissions) {
                    return;
                }
                if ("disable" === scope.behavior) {
                    for (var i = 0; i < element.length; i++) {
                        element[i].disabled = true;
                    }
                } else {
                    element.hide();
                }

                var dfd = $q.defer();
                $timeout(function () {
                    authService.hasAuthority(scope.permissions).then(function (response) {
                        if ("disable" === scope.behavior) { //default behavior is to disable
                            if (response) {
                                for (var i = 0; i < element.length; i++) {
                                    element[i].disabled = false;
                                }
                            }
                        } else {
                            if (response) {
                                element.show();
                            } else {
                                element.hide();
                            }
                        }
                        dfd.resolve();
                    });
                }, 100);
                return dfd.promise;
            }
        }
    }]);