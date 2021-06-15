angular.module('lite').controller('notificationsPopupController',
    ['$scope', '$uibModalInstance', 'Notification', '$rootScope', 'userNotifications',
        function ($scope, $uibModalInstance, Notification, $rootScope, userNotifications) {

            $scope.userNotifications = userNotifications;

            $scope.close = function () {
                $uibModalInstance.close(false);
            };
        }
    ]
);