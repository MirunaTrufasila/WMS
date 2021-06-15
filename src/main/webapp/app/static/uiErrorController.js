angular.module('lite').controller('uiErrorController',
    ['$scope', '$routeParams', '$location',
        function ($scope, $routeParams, $location) {

            $scope.errResponse = $routeParams['error'];
            $scope.goto = $routeParams['goto'];

            if ($scope.errResponse) {
                $scope.handleErrorNotification($scope.errResponse);
            }

            $scope.gotoPage = function () {
                $location.$$search = {};
                $location.path($scope.goto).search();
            }
        }
    ]
);