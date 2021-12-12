'use strict';

angular.module('lite', [
    'ui.validate',
    'ui-notification',
    'ui.bootstrap',
    'ngRoute',
    'ngCookies',
    'darthwade.loading',
    'angular-loading-bar',
    'ngAnimate',
    'ngSanitize',
    'isteven-multi-select',
    'ng-breadcrumbs',
    'ngMaterial',
    'ngMessages',

    'ui.grid',
    'ui.grid.cellNav',
    'ui.grid.edit',
    'ui.grid.resizeColumns',
    'ui.grid.pinning',
    'ui.grid.selection',
    'ui.grid.moveColumns',
    'ui.grid.exporter',
    'ui.grid.importer',
    'ui.grid.grouping',
    'ui.grid.saveState',
    'ui.grid.autoResize',
    'ui.grid.expandable',

    'ui.bootstrap.pagination', //see https://github.com/angular-ui/bootstrap/tree/master/src/pagination
    'pascalprecht.translate',
    'angularjs-dropdown-multiselect',
    'TreeWidget',
    'dndLists'
])

    .constant('PRIVILEGES', {
        USERS: 1,
        USERS_ADD: 2,
        USERS_EDIT: 3,
        USERS_DEL: 4
    })


    // .config(function (NotificationProvider) {
    //     NotificationProvider.setOptions({
    //         delay: 10000,
    //         startTop: 78,
    //         startRight: 10,
    //         verticalSpacing: 5,
    //         horizontalSpacing: 5,
    //         positionX: 'right',
    //         positionY: 'top',
    //         closeOnClick: true,
    //         maxCount: 5,
    //         templateUrl: 'app/components/ui_notifications/ui-notification-tmpl.html'
    //     });
    // })

    .config(function (cfpLoadingBarProvider) { // https://github.com/chieffancypants/angular-loading-bar
        cfpLoadingBarProvider.includeSpinner = true;
        cfpLoadingBarProvider.includeBar = true;
        cfpLoadingBarProvider.parentSelector = '#loading-bar-container';
        // TODO include custom template and message
        // cfpLoadingBarProvider.spinnerTemplate = '<div><span class="fa fa-spinner"> Loading...</div>';
    })

    .config(['$routeProvider', 'PRIVILEGES',
        function ($routeProvider, PRIVILEGES) {
            $routeProvider
                .when('/', {
                    templateUrl: 'app/components/root.html',
                    label: 'general.breadcrumbs.HOME'
                })

                .when('/administration', {
                    templateUrl: 'app/components/admin/administration.html',
                    label: 'general.breadcrumbs.ADMINISTRATION',
                    params: {
                        menuCategory: 'admin'
                    }
                })

                .when('/users', {
                    templateUrl: 'app/components/users/users.html',
                    controller: 'usersController',
                    label: 'general.breadcrumbs.USERS'
                })
                .when('/users/create', {
                    templateUrl: 'app/components/users/user.html',
                    controller: 'userController',
                    resolve: {
                        'resolvedUser': dummyResolve
                    }
                })
                .when('/users/edit/:id', {
                    templateUrl: 'app/components/users/user.html',
                    controller: 'userController',
                    resolve: {
                        'resolvedUser': function (userService, $route, $location) {
                            showPleaseWait();
                            return userService.getUser($route.current.params.id)
                                .then(function onSuccess(response) {
                                    hidePleaseWait();
                                    return response.data;
                                }).catch(function onError(response) {
                                    hidePleaseWait();
                                    $location.path('/error').search({error: response, goto: 'users'});
                                });
                        }
                    }
                })

                .when('/user-privileges/edit/:id', {
                    templateUrl: 'app/components/users/user_privileges.html',
                    // permission: PRIVILEGES.CONFIGURARE_DREPTURI_UTILIZATORI,
                    controller: 'userPrivilegesController',
                    resolve: {
                        'resolvedUser': function (userPrivilegesService, $route, $location) {
                            showPleaseWait();
                            return userPrivilegesService.getUser($route.current.params.id)
                                .then(function onSuccess(response) {
                                    hidePleaseWait();
                                    return response.data;
                                }).catch(function onError(response) {
                                    hidePleaseWait();
                                    $location.path('/error').search({error: response, goto: 'users'});
                                });
                        },
                        "resolvedUserPrivileges": function (userPrivilegesService, $route, $location) {
                            showPleaseWait();
                            return userPrivilegesService.getUserPrivileges($route.current.params.id)
                                .then(function onSuccess(response) {
                                    hidePleaseWait();
                                    return response.data;
                                }).catch(function onError(response) {
                                    hidePleaseWait();
                                    $location.path('/error').search({error: response, goto: 'users'});
                                });
                        }
                    }
                })
                // .when('/company-details', {
                //     templateUrl: 'app/components/company_details/company_details.html',
                //     label: 'general.breadcrumbs.COMPANY_DETAILS',
                //     params: {
                //         menuCategory: 'nomenclatures'
                //     }
                // })
                // .when('/employees', {
                //     templateUrl: 'app/components/angajati/angajati.html',
                //     controller: 'angajatiController',
                //     label: 'general.breadcrumbs.ANGAJATI',
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                // .when('/employees/create', {
                //     templateUrl: 'app/components/angajati/angajat.html',
                //     controller: 'angajatController',
                //     resolve: {
                //         'resolvedEmployee': dummyResolve,
                //     },
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                // .when('/employees/edit/:id', {
                //     templateUrl: 'app/components/angajati/angajat.html',
                //     controller: 'angajatController',
                //     resolve: {
                //         'resolvedEmployee': function (angajatiService, $route, $location) {
                //             showPleaseWait();
                //             return angajatiService.getAngajat($route.current.params.id)
                //                 .then(function onSuccess(response) {
                //                     hidePleaseWait();
                //                     return response.data;
                //                 }).catch(function onError(response) {
                //                     hidePleaseWait();
                //                     $location.path('/error').search({error: response, goto: 'employees'});
                //                 });
                //         },
                //     },
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                //
                // .when('/facturi', {
                //     templateUrl: 'app/components/facturi/facturi.html',
                //     controller: 'facturiController',
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                // .when('/facturi/create', {
                //     templateUrl: 'app/components/facturi/factura.html',
                //     controller: 'facturaController',
                //     resolve: {
                //         'resolvedFactura': dummyResolve,
                //     },
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                // .when('/facturi/edit/:id', {
                //     templateUrl: 'app/components/facturi/factura.html',
                //     controller: 'facturaController',
                //     resolve: {
                //         'resolvedFactura': function (facturiService, $route, $location) {
                //             showPleaseWait();
                //             return facturiService.getFactura($route.current.params.id)
                //                 .then(function onSuccess(response) {
                //                     hidePleaseWait();
                //                     return response.data;
                //                 }).catch(function onError(response) {
                //                     hidePleaseWait();
                //                     $location.path('/error').search({error: response, goto: 'facturi'});
                //                 });
                //         },
                //     },
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                //
                // .when('/furnizori', {
                //     templateUrl: 'app/components/furnizori/furnizori.html',
                //     controller: 'furnizoriController',
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                // .when('/furnizori/create', {
                //     templateUrl: 'app/components/furnizori/furnizor.html',
                //     controller: 'furnizorController',
                //     resolve: {
                //         'resolvedFurnizor': dummyResolve,
                //     },
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                // .when('/furnizori/edit/:id', {
                //     templateUrl: 'app/components/furnizori/furnizor.html',
                //     controller: 'furnizorController',
                //     resolve: {
                //         'resolvedFurnizor': function (furnizorService, $route, $location) {
                //             showPleaseWait();
                //             return furnizorService.getFurnizor($route.current.params.id)
                //                 .then(function onSuccess(response) {
                //                     hidePleaseWait();
                //                     return response.data;
                //                 }).catch(function onError(response) {
                //                     hidePleaseWait();
                //                     $location.path('/error').search({error: response, goto: 'furnizori'});
                //                 });
                //         },
                //     },
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                //
                // .when('/debug', {
                //     templateUrl: 'app/components/debug/debug.html',
                //     controller: 'debugController',
                //     permission: PRIVILEGES.DEBUG,
                //     params: {
                //         menuCategory: 'master-data'
                //     }
                // })
                //
                // .when('/activities', {
                //     templateUrl: 'app/components/user_activity/activities.html',
                //     controller: 'activitiesController',
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                //
                // .when('/errors', {
                //     templateUrl: 'app/components/errors/errors.html',
                //     controller: 'errorsController',
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                // .when('/errors/edit/:id', {
                //     templateUrl: 'app/components/errors/error.html',
                //     controller: 'errorController',
                //     resolve: {
                //         'resolvedError': function (errorService, $route, $location) {
                //             showPleaseWait();
                //             return errorService.getError($route.current.params.id)
                //                 .then(function onSuccess(response) {
                //                     hidePleaseWait();
                //                     return response.data;
                //                 }).catch(function onError(response) {
                //                     hidePleaseWait();
                //                     $location.path('/error').search({error: response, goto: 'errors'});
                //                 });
                //         }
                //     },
                //     params: {
                //         menuCategory: 'admin'
                //     }
                // })
                .when('/access-denied', {
                    templateUrl: 'app/static/access-denied.html',
                    label: 'general.breadcrumbs.ACCESS_DENIED'
                })
                .when('/logout', {
                    redirectTo: '/logout',
                    label: 'general.breadcrumbs.LOGOUT'
                })
                .when('/home', {
                    redirectTo: '/'
                })
                // .when('/error', {
                //     templateUrl: 'app/static/uiError.html',
                //     controller: 'uiErrorController',
                //     label: 'general.breadcrumbs.ERROR_PAGE'
                // })
                // .when('/notification', {
                //     templateUrl: 'app/components/notification/notification.html',
                //     controller: 'notificationController',
                //     label: 'general.breadcrumbs.NOTIFICATION',
                //     params: {
                //         menuCategory: 'alerts'
                //     }
                // })
                .otherwise({
                    redirectTo: '/'
                });
        }])

    .config(['$locationProvider', function ($locationProvider) {
        $locationProvider.hashPrefix('');
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: true
        });
    }])

    .config(['$translateProvider', function ($translateProvider) {
        $translateProvider
            .useLocalStorage()
            .useStaticFilesLoader({
                prefix: 'app/i18n/messages_',
                suffix: '.json'
            })
            .preferredLanguage('ro')
            // .fallbackLanguage('en')
            .forceAsyncReload(true)
            .useSanitizeValueStrategy(null) // using 'escape' caused issues with ' sign, using 'sanitize' prevents display of utf-8 chars
            .useMissingTranslationHandlerLog();
    }])

    .config(['$compileProvider', function ($compileProvider) {
        $compileProvider.imgSrcSanitizationWhitelist(/^\s*(http?|local|data|blob|chrome-extension):/);
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|blob):/);
    }])

    .config(function ($mdThemingProvider, $mdInkRippleProvider) {
        $mdInkRippleProvider.disableInkRipple();

        $mdThemingProvider
            .definePalette('ltsPalette', {
                '50': 'ffebee',
                '100': 'ffcdd2',
                '200': 'ef9a9a',
                '300': 'e57373',
                '400': 'ef5350',

                '500': 'f44336',
                '600': 'e53935',
                '700': 'd32f2f',
                '800': 'c62828',
                '900': 'b71c1c',

                'A100': 'ff8a80',
                'A200': 'ff5252',
                'A400': 'ff1744',
                'A700': 'd50000',

                'contrastDefaultColor': 'light'
            });

        $mdThemingProvider
            .theme('ltsTheme')
            .primaryPalette('ltsPalette')
            .accentPalette('ltsPalette')

    })

    .run(['$rootScope', '$location', 'authService', function ($rootScope, $location, authService) {
        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (next.$$route && next.$$route.originalPath === '/logout') {
                authService.logout();
            }
        });

        $rootScope.$on('$routeChangeError', function (event, current, previous, rejection) {
            console.log('error changing route: ' + rejection);
        });
    }]);

var dummyResolve = function () {
    return {};
};