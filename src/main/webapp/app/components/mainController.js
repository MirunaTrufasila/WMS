angular.module('lite').controller('mainController',
    [
        '$scope',
        '$uibModal',
        'Notification',
        '$loading',
        'cfpLoadingBar',
        '$timeout',
        '$http',
        'adminService',
        '$location',
        'authService',
        '$translate',
        '$rootScope',
        '$filter',
        '$window',
        '$document',
        '$route',
        '$routeParams',
        'breadcrumbs',
        'PRIVILEGES',
        'APP_MENU',
        function (
            $scope,
            $uibModal,
            Notification,
            $loading,
            cfpLoadingBar,
            $timeout,
            $http,
            adminService,
            $location,
            authService,
            $translate,
            $rootScope,
            $filter,
            $window,
            $document,
            $route,
            $routeParams,
            breadcrumbs,
            PRIVILEGES,
            APP_MENU) {

            $scope.onFilterDropdownOpen = function (activatedGridFilter) {
                // emit an event that we listen to in the setChildToPositionFixed DIRECTIVE/S across the whole app
                // this means the dropdown was open and the actual dropdown menu is available in DOM
                $rootScope.$emit('filterDropdownOpen', activatedGridFilter);
            };

            // $route prefix used by rendered breadcrumbs, see index.html. Is the context path (e.g. /lts/) minus the last slash
            $scope.routePrefixForBreadcrumbs = contextPath.substr(0, contextPath.length - 1);
            $scope.breadcrumbs = breadcrumbs; // get injected breadcrumbs into controller to render in template

            $scope.availableLanguages = [ // available languages used in template to render language options
                {langKey: 'ro', label: 'Română', cssClass: 'flag-icon flag-icon-ro'},
                {langKey: 'en', label: 'English', cssClass: 'flag-icon flag-icon-us'}
            ];

            $scope.checkIfMobileDevice = {
                Android: function () {
                    return navigator.userAgent.match(/Android/i);
                },
                BlackBerry: function () {
                    return navigator.userAgent.match(/BlackBerry/i);
                },
                iOS: function () {
                    return navigator.userAgent.match(/iPhone|iPad|iPod/i);
                },
                Opera: function () {
                    return navigator.userAgent.match(/Opera Mini/i);
                },
                Windows: function () {
                    return navigator.userAgent.match(/IEMobile/i) || navigator.userAgent.match(/WPDesktop/i);
                },
                get any() {
                    return (
                        this.Android() ||
                        this.BlackBerry() ||
                        this.iOS() ||
                        this.Opera() ||
                        this.Windows());
                }
            };

            $scope.isMobileDevice = !!$scope.checkIfMobileDevice.any;
            $scope.toggleMenuForMobile = function () {
                $scope.showMenuForMobile = !$scope.showMenuForMobile;
            };

            $scope.pageSize = 20;
            $scope.treeSearchString = "";
            $scope.companyName = "Lite Technologies Softare";
            $scope.minimized = false;

            $scope.navigationItems = APP_MENU;
            $scope.PRIVILEGES = PRIVILEGES;

            /*** Header */
            $scope.tabToActivate = {};

            $scope.navigateHome = function () {
                $location.path($scope.routePrefixForBreadcrumbs);
            };

            $scope.resetAllMenus = function () {
                angular.forEach($scope.navigationItems, function (item, index) {
                    $scope.tabToActivate[$scope.navigationItems[index].name] = false;
                });
            };

            $scope.resetAllMenus();

            $scope.closeSecondaryNav = function () {
                $scope.resetAllMenus();
            };

            $scope.$on('$routeChangeSuccess', function (event, nextRoute) {
                setActiveMenu(nextRoute);
            });

            $scope.$on('$routeChangeStart', function () {
                // on route change reset active menus and close secondaryNavigation
                $scope.resetAllMenus();
                $scope.closeSecondaryNav();
            });

            /*** end of header functionality */

            $scope.minMax = function () {
                //  $('.ui-grid').css('height', '300px !important;');
                $scope.minimized = !$scope.minimized;
                var mainRightArea = $('#mainRightArea');
                var mainLeftArea = $('#mainLeftArea');
                if ($scope.minimized) {
                    mainRightArea.removeClass("col-xs-11");
                    mainRightArea.addClass("col-xs-12");
                    mainLeftArea.removeClass("col-xs-1");
                } else {
                    mainRightArea.removeClass("col-xs-12");
                    mainRightArea.addClass("col-xs-11");
                    mainLeftArea.addClass("col-xs-1");
                }
            };

            $scope.changeLanguage = function changeLangFn(langKey) {
                setCurrentLang(langKey);

                $translate.use(langKey).then(function onSuccess(response) {
                    // $scope.buildTree(); - uncomment this to get the tree on left side
                    $route.reload();
                }).catch(function onError(response) {
                    console.error(response);
                }).finally(function () {
                    $('#logoutNavItem').attr("title", $rootScope.getI18nMessage("general.LOGOUT"));
                    $('#logoutNavItem2').text($rootScope.getI18nMessage("general.LOGOUT"));
                });
            };

            $scope.logout = function () {
                $location.path("/logout");
            };

            $scope.goto = function (path) {
                $location.path(path);
            };

            //NG_TRANSLATE_LANG_KEY = default key for current translate language
            var currentLang = $window.localStorage.getItem("NG_TRANSLATE_LANG_KEY");
            if (currentLang) {
                setCurrentLang(currentLang);
                $scope.changeLanguage(currentLang); //live language change!
            } else {
                setCurrentLang($translate.preferredLanguage());
                $('#logoutNavItem').attr("title", $rootScope.getI18nMessage("general.LOGOUT"));
                $('#logoutNavItem2').text($rootScope.getI18nMessage("general.LOGOUT"));
            }

            $scope.EXPECTATION_FAILED = 417; // for validation errors - will display warnings

            $scope.getCurrentUiLanguage = function () {
                return $translate.proposedLanguage() || $translate.use();
            };

            $scope.treeOptions = {
                bootstrap2: false,
                showTags: true,
                width: 500,
                levels: 0,
                silent: true,
                onNodeSelected: function (event, node) {
                    $timeout(function () {
                        $location.path(node.url);
                    }, 0);
                },
                onNodeUnselected: function (event, node) {
                    if (node.nodes !== undefined) {
                    }
                }
            };

            $scope.getUserResource = function () {
                authService.getUserResource()
                    .then(function (response) {
                        $rootScope.userResource = response.data;
                        $scope.userResource = response.data;
                    });
            };

            $scope.getUserResource();

            $scope.handleErrorNotification = function (response) {
                console.log(response);
                if (response.status === 401 || response.status === 403) {
                    return;
                }
                if (response.status === $scope.EXPECTATION_FAILED) {
                    Notification.warning(response.data.message);
                } else if (response.status === 500 && response.data.message.includes("ConstraintViolationException")) {
                    Notification.error($scope.getI18nMessage('general.SQL_CONSTRAINT_VIOLATION'));
                } else {
                    var message;
                    try {
                        message = response.data.error + "(" + response.data.status + ")" + ": " + response.data.message;
                    } catch (exc) {
                        message = response.data ? response.data.toString() : (response.message ? response.message : "A intervenit o eroare!");
                    } finally {
                        Notification.error(message);
                    }
                }
            };

            $rootScope.handleErrorNotification = function (response) {
                $scope.handleErrorNotification(response);
            };

            $rootScope.getI18nMessage = function (message, params) {
                return $scope.getI18nMessage(message, params);
            };

            $scope.getI18nMessage = function (message, params) {
                return $filter('translate')(message, params);
            };

            $scope.reloadProperties = function () {
                adminService.getApplicationProperties()
                    .then(function onSuccess(response) {
                        $scope.userGroups = response.data.userGroups;
                        $scope.showNotificationsPopupForCurrentUser(response.data.notifications);

                        $scope.languages = response.data.languages;
                        $scope.activityCategories = response.data.activityCategories;
                        $scope.errorTypes = response.data.errorTypes;
                        $scope.userRoles = response.data.userRoles;
                    })
                    .catch(function onError(response) {
                        $scope.handleErrorNotification(response);
                    });
            };

            $scope.reloadProperties();

            $scope.showNotificationsPopupForCurrentUser = function (notifications) {
                if (!notifications || notifications.length === 0) {
                    return;
                }
                var modalInstance = $uibModal.open({
                    animation: true,
                    controller: 'notificationsPopupController',
                    templateUrl: 'app/components/admin/notificationsPopup.html',
                    windowClass: 'user-notifications-modal-window',
                    backdrop: 'static', // prevents modal from closing by clicking outside his area
                    keyboard: true, // allows modal to be closed by pressing escape
                    resolve: {
                        userNotifications: function () {
                            return notifications;
                        }
                    }
                });

                modalInstance.result.then(function (result) {
                    // use promise.resolve(result) from .close()
                    $scope.finishNotifications(notifications);
                }, function (result) {
                    // handle promise.reject(), or leave method empty to do nothing, whatever!
                });
            };

            $scope.finishNotifications = function (notifications) {
                adminService.finishNotifications(notifications)
                    .then(function onSuccess(response) {
                        // do nothing
                    })
                    .catch(function onError(response) {
                        $scope.handleErrorNotification(response);
                    });
            };

            $scope.validateField = function (formField, value) {
                if (value === '' || value === undefined)
                    formField.addClass("has-error");
                else
                    formField.removeClass("has-error");
            };

            $scope.openWindow = function (url, title, w, h) {
                // Fixes dual-screen position                         Most browsers      Firefox
                var dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : window.screenX;
                var dualScreenTop = window.screenTop !== undefined ? window.screenTop : window.screenY;

                var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
                var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

                var left = ((width / 2) - (w / 2)) + dualScreenLeft;
                var top = ((height / 2) - (h / 2)) + dualScreenTop;
                var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

                // Puts focus on the newWindow
                if (window.focus) {
                    newWindow.focus();
                }
            };

            $rootScope.openWindow = function (url, title, w, h) {
                return $scope.openWindow(url, title, w, h);
            };

            $scope.getActivityCellBackground = function (activity) {
                if (!activity) {
                    return '';
                } else if (activity.method === 'DELETE') {
                    return "method-delete";
                }
                return "";
            };

            $rootScope.getActivityCellBackground = function (activity) {
                return $scope.getActivityCellBackground(activity);
            };

            $scope.getFacturiCellBackground = function (facturi) {
                if (!facturi) {
                    return '';
                } else if (facturi.achitata === false) {
                    return "method-neachitat";
                } else if (facturi.achitata === true) {
                    return "method-achitat";
                }
                return "";
            };

            $rootScope.getFacturiCellBackground = function (facturi) {
                return $scope.getFacturiCellBackground(facturi);
            };

            $rootScope.getDropDownCustomTexts = function () {
                return {
                    buttonDefaultText: $rootScope.getI18nMessage('general.fields.NO_SELECT'),
                    checkAll: $rootScope.getI18nMessage('general.fields.SELECT_ALL'),
                    uncheckAll: $rootScope.getI18nMessage('general.fields.DESELECT_ALL'),
                    dynamicButtonTextSuffix: $rootScope.getI18nMessage('general.fields.SELECTED')
                };
            };

            $rootScope.getActivitiesMethods = function () {
                return [
                    {value: 'GET', label: $rootScope.getI18nMessage('activities.methods.GET')},
                    {value: 'POST', label: $rootScope.getI18nMessage('activities.methods.POST')},
                    {value: 'PUT', label: $rootScope.getI18nMessage('activities.methods.PUT')},
                    {value: 'DELETE', label: $rootScope.getI18nMessage('activities.methods.DELETE')}
                ]
            };

            $rootScope.getErrorLevels = function () {
                return [
                    {value: 'ERROR', label: $rootScope.getI18nMessage('errors.level.ERROR')},
                    {value: 'WARNING', label: $rootScope.getI18nMessage('errors.level.WARNING')},
                    {value: 'INFO', label: $rootScope.getI18nMessage('errors.level.INFO')}
                ]
            };

            $scope.removeFromGrid = function (input, id) {
                for (var i = 0; i < input.length; i++) {
                    if (input[i].id === id) {
                        input.splice(i, 1);
                        break;
                    }
                }
            };

            function setActiveMenu(nextRoute) { // set active menu after route is changed
                var nextRouteParams = nextRoute.$$route.params;
                var nextRouteParamsKeys = undefined;
                $scope.activeMenu = {};

                $scope.navigationItems.forEach(function (navItem) {
                    $scope.activeMenu[navItem.name] = false; // set to false all menus
                });

                nextRouteParams
                    ? nextRouteParamsKeys = Object.keys(nextRouteParams)
                    : nextRouteParamsKeys = [];

                nextRouteParamsKeys.forEach(function (paramKey) {
                    if (paramKey === 'menuCategory') {
                        $scope.activeMenu[nextRouteParams[paramKey]] = true;
                    }
                });
            }

            // utility f() > set $scope.currentLanguage as object{} in $scope.availableLanguages based on lankKey stored in localStorage
            function setCurrentLang(langKey) {
                angular.forEach($scope.availableLanguages, function (availableLanguage) {
                    if (availableLanguage.langKey === langKey) {
                        $scope.currentLanguage = availableLanguage;
                    }
                });
            }

            $scope.setParentStateOnChildren = function (node, tree) {
                if (!node.nodes || node.nodes.length === 0) {
                    return;
                }
                angular.forEach(node.nodes, function (children) {
                    children.state = node.state;
                    if (children.state.checked) {
                        tree.treeview('checkNode', [children.nodeId, {silent: true}]);
                    } else {
                        tree.treeview('uncheckNode', [children.nodeId, {silent: true}]);
                    }
                    $scope.setParentStateOnChildren(children, tree);
                });
            };

            $scope.checkParents = function (node, tree) {
                var parent = tree.treeview('getParent', node);
                if (parent === tree || parent.nodeId === undefined) {
                    return;
                }
                if (node && node.state && node.state.checked) {
                    tree.treeview('checkNode', [parent.nodeId, {silent: true}]);
                    $scope.checkParents(parent, tree);
                }
            };

            $scope.openCalendarOnButton = function ($event) {
                $($event.currentTarget.closest('div')).find('input')[0].focus();
            };

        }
    ]
);
