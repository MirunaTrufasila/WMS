angular.module('lite').controller('employeesReductionController',
    ['$scope', 'employeeReductionService', 'uiGridConstants', 'Notification', '$location',
        function ($scope, employeeReductionService, uiGridConstants, Notification, $location) {

            $scope.employeeReduction = [];
            $scope.totalEmployeeReduction = 0;
            $scope.searchTerm = employeeReductionService.searchTerm;

            $scope.searchEmployeeReduction = function () {
                employeeReductionService.searchEmployeeReduction($scope.searchTerm, $scope.employeeReductionGrid.employeeReductionCurrentPage,
                    $scope.employeeReductionGrid.paginationPageSize)
                    .then(function onSuccess(response) {
                        $scope.employeeReduction = response.data.content;
                        $scope.totalEmployeeReduction = response.data.totalElements;
                    }).catch(function onError(response) {
                    $scope.handleErrorNotification(response);
                });
            };

            $scope.uploadFile = function () {
                employeeReductionService.uploadFile($scope.employeeReduction.fileContent, $scope.employeeReduction.fileName)
                    .then(function onSuccess(response) {
                        $scope.employeeReduction = response.data.content;
                        $scope.totalEmployeeReduction = response.data.totalElements;
                    }).catch(function onError(response) {
                    $scope.handleErrorNotification(response);
                });

            };

            $scope.triggerSearch = function (event) {
                employeeReductionService.searchTerm = $scope.searchTerm;
                var code = event ? (event.keyCode ? event.keyCode : event.which) : '';
                if (event === null || code === 13 || code === 1) {
                    $scope.searchEmployeeReduction();
                }
            };

            $scope.filterEmployeeReductionGrid = function (pageNumber) {
                $scope.employeeReductionGrid.employeeReductionCurrentPage = pageNumber;
                if ($scope.searchTerm) {
                    $scope.searchEmployeeReduction();
                    return;
                }

                employeeReductionService.getEmployeesReduction($scope.employeeReductionGrid.employeeReductionCurrentPage,
                    $scope.employeeReductionGrid.paginationPageSize)
                    .then(function onSuccess(response) {
                        $scope.employeeReduction = response.data.content;
                        $scope.totalEmployeeReduction = response.data.totalElements;
                    })
                    .catch(function onError(response) {
                        $scope.handleErrorNotification(response);
                    });
            };

            $scope.createEmployeeReduction = function () {
                $location.path("/employees-reduction/create");
            };

            $scope.editEmployeeReduction = function (employeeReduction) {
                $location.path("/employees-reduction/edit/" + employeeReduction.no);
            };

            $scope.employeeReductionGrid = {
                data: 'employeeReduction',
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
                employeeReductionCurrentPage: 1,
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
                rowTemplate: "<div ng-dblclick=\"grid.appScope.editEmployeeReduction(row.entity)\" " +
                    "ng-repeat=\"(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name\" " +
                    "class=\"ui-grid-cell\" ng-class=\"{ 'ui-grid-row-header-cell': col.isRowHeader }\" ui-grid-cell></div>"
            };
        }
    ]
);