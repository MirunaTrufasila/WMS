angular.module('lite').controller('employeeReductionController',
    ['$scope', 'employeeReductionService', 'uiGridConstants', 'Notification', '$location', '$routeParams', 'resolvedEmployeesReduction',
        function ($scope, employeeReductionService, uiGridConstants, Notification, $location, $routeParams, resolvedEmployeesReduction) {

            $scope.employeeReduction = resolvedEmployeesReduction;
            $scope.employeeReductionItems = [];
            $scope.totalEmployeeReductionItems = 0;

            $scope.back = function () {
                $location.path("/employees-reduction");
            };

            // $scope.saveUser = function () {
            //     if ($scope.employeeReduction.password && $scope.employeeReduction.password !== $scope.employeeReduction.passwordUpdate) {
            //         Notification.warning($scope.getI18nMessage('users.messages.PASSWORD_WARNING'));
            //         return;
            //     }
            //     if ($scope.employeeReduction.id) {
            //         employeeReductionService.updateUser($scope.employeeReduction)
            //             .then(function onSuccess(response) {
            //                 $scope.employeeReduction = response.data;
            //                 Notification.success($scope.getI18nMessage('users.messages.UPDATE_CONFIRMATION'));
            //             }).catch(function onError(response) {
            //             $scope.handleErrorNotification(response);
            //         });
            //     } else {
            //         employeeReductionService.createUser($scope.employeeReduction)
            //             .then(function onSuccess(response) {
            //                 $scope.employeeReduction = response.data;
            //                 Notification.success($scope.getI18nMessage('users.messages.CREATE_CONFIRMATION'));
            //             }).catch(function onError(response) {
            //             $scope.handleErrorNotification(response);
            //         });
            //     }
            // };

            $scope.filterEmployeeReductionGrid = function (pageNumber) {
                $scope.employeeReductionItemsGrid.employeeReductionItemCurrentPage = pageNumber;
                if ($scope.searchTerm) {
                    $scope.searchEmployeeReduction();
                    return;
                }

                employeeReductionService.getEmployeesReduction($scope.employeeReductionItemsGrid.employeeReductionItemCurrentPage,
                    $scope.employeeReductionItemsGrid.paginationPageSize)
                    .then(function onSuccess(response) {
                        $scope.employeeReduction = response.data.content;
                        $scope.totalEmployeeReductionItems = response.data.totalElements;
                    })
                    .catch(function onError(response) {
                        $scope.handleErrorNotification(response);
                    });
            };

            $scope.searchEmployeeReduction = function () {
                employeeReductionService.searchEmployeeReduction($scope.searchTerm, $scope.employeeReductionItemsGrid.employeeReductionItemCurrentPage,
                    $scope.employeeReductionItemsGrid.paginationPageSize)
                    .then(function onSuccess(response) {
                        $scope.employeeReduction = response.data.content;
                        $scope.totalEmployeeReductionItems = response.data.totalElements;
                    }).catch(function onError(response) {
                    $scope.handleErrorNotification(response);
                });
            };

            $scope.employeeReductionItemsGrid = {
                data: 'employeeReductionItemsGrid',
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
                employeeReductionItemCurrentPage: 1,
                minimumColumnSize: 150,
                exporterExcelCustomFormatters: function (grid, workbook, docDefinition) {
                    return "";
                },
                onRegisterApi: function (gridApi) {
                    $scope.gridApi = gridApi;

                    gridApi.core.on.renderingComplete($scope, function () {
                        $scope.filterEmployeeReductionGrid(1);
                    });
                },
                columnDefs: [
                    {
                        field: 'fileName',
                        enableFiltering: false,
                        displayName: $scope.getI18nMessage('employeeReduction.view.FIELD_USERNAME')
                    },
                    {
                        field: 'id',
                        displayName: $scope.getI18nMessage('employeeReduction.view.FIELD_ID'),
                        width: 50,
                        enableHiding: false,
                        enableFiltering: false
                    }
                ],
                // rowTemplate: "<div ng-dblclick=\"grid.appScope.editEmployeeReduction(row.entity)\" " +
                //     "ng-repeat=\"(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name\" " +
                //     "class=\"ui-grid-cell\" ng-class=\"{ 'ui-grid-row-header-cell': col.isRowHeader }\" ui-grid-cell></div>"
            };
        }
    ]
);