angular.module('lite').controller('usersController',
    ['$scope', 'userService', 'uiGridConstants', 'Notification', '$location',
        function ($scope, userService, uiGridConstants, Notification, $location) {

            $scope.users = [];
            $scope.totalUsers = 0;
            $scope.searchTerm = userService.searchTerm;

            $scope.filterUsersGrid = function (pageNumber) {
                $scope.usersGrid.usersCurrentPage = pageNumber;
                if ($scope.searchTerm) {
                    $scope.searchUser();
                    return;
                }

                userService.get($scope.usersGrid.usersCurrentPage, $scope.usersGrid.paginationPageSize)
                    .then(function onSuccess(response) {
                        $scope.users = response.data.content;
                        $scope.totalUsers = response.data.totalElements;
                    })
                    .catch(function onError(response) {
                        $scope.handleErrorNotification(response);
                    });
            };

            $scope.createUser = function () {
                $location.path("/users/create");
            };

            $scope.editUser = function (user) {
                $location.path("/users/edit/" + user.id);
            };

            $scope.editUserPrivileges = function (user) {
                $location.path("/user-privileges/edit/" + user.id);
            };

            $scope.deleteUser = function (user) {
                if (!confirm($scope.getI18nMessage('users.messages.DELETE_QUESTION'))) {
                    return;
                }
                userService.deleteUser(user.id)
                    .then(function onSuccess(response) {
                        $scope.removeFromGrid($scope.users, user.id);
                        Notification.success($scope.getI18nMessage('users.messages.DELETE_CONFIRMATION'))
                    }).catch(function onError(response) {
                    $scope.handleErrorNotification(response);
                });
            };

            $scope.triggerSearch = function (event) {
                userService.searchTerm = $scope.searchTerm;
                var code = event ? (event.keyCode ? event.keyCode : event.which) : '';
                if (event === null || code === 13 || code === 1) {
                    $scope.searchUser();
                }
            };

            $scope.searchUser = function () {
                userService.searchUser($scope.searchTerm, $scope.usersGrid.usersCurrentPage,
                    $scope.usersGrid.paginationPageSize)
                    .then(function onSuccess(response) {
                        $scope.users = response.data.content;
                        $scope.totalUsers = response.data.totalElements;
                    }).catch(function onError(response) {
                    $scope.handleErrorNotification(response);
                });
            };

            $scope.usersGrid = {
                data: 'users',
                enableFiltering: true,
                exporterMenuCsv: true,
                enableGridMenu: true,
                showGridFooter: false,
                showColumnFooter: false,
                enableSelectAll: false,
                enableFullRowSelection: true,
                enableRowSelection: true,
                enableRowHeaderSelection: false,
                multiSelect: false,
                paginationPageSize: 20,
                paginationPageSizes: [5, 10, 15, 20, 50, 100, 500, 1000],
                usersCurrentPage: 1,
                minimumColumnSize: 150,
                exporterExcelCustomFormatters: function (grid, workbook, docDefinition) {
                    return "";
                },
                onRegisterApi: function (gridApi) {
                    $scope.gridApi = gridApi;

                    gridApi.core.on.renderingComplete($scope, function () {
                        $scope.filterUsersGrid(1);
                    });
                },
                columnDefs: [
                    {
                        name: 'actions',
                        width: 120,
                        headerCellTemplate: '<div></div>',
                        enableFiltering: false,
                        cellTemplate: 'app/components/users/user_grid_actions_template.html'
                    },
                    {
                        field: 'username',
                        enableFiltering: false,
                        displayName: $scope.getI18nMessage('users.view.FIELD_USERNAME')
                    },
                    {
                        field: 'firstName',
                        enableFiltering: false,
                        displayName: $scope.getI18nMessage('users.view.FIELD_NAME')
                    },
                    {
                        field: 'lastName',
                        enableFiltering: false,
                        displayName: $scope.getI18nMessage('users.view.FIELD_SURNAME')
                    },
                    {
                        field: 'email',
                        enableFiltering: false,
                        displayName: $scope.getI18nMessage('users.view.FIELD_EMAIL')
                    },
                    {
                        name: 'phone',
                        enableFiltering: false,
                        displayName: $scope.getI18nMessage('users.view.FIELD_PHONE'),
                    },
                    {
                        field: 'id',
                        displayName: $scope.getI18nMessage('users.view.FIELD_ID'),
                        width: 50,
                        enableHiding: false,
                        enableFiltering: false
                    }
                ],
                rowTemplate: "<div ng-dblclick=\"grid.appScope.editUser(row.entity)\" " +
                    "ng-repeat=\"(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name\" " +
                    "class=\"ui-grid-cell\" ng-class=\"{ 'ui-grid-row-header-cell': col.isRowHeader }\" ui-grid-cell></div>"
            };
        }
    ]
);