angular.module('lite').controller('userController',
    ['$scope', 'userService', 'uiGridConstants', 'Notification', '$location', '$routeParams', 'resolvedUser',
        function ($scope, userService, uiGridConstants, Notification, $location, $routeParams, resolvedUser) {

            $scope.editUser = resolvedUser;

            $scope.back = function () {
                $location.path("/users");
            };

            $scope.saveUser = function () {
                if ($scope.editUser.password && $scope.editUser.password !== $scope.editUser.passwordUpdate) {
                    Notification.warning($scope.getI18nMessage('users.messages.PASSWORD_WARNING'));
                    return;
                }
                if ($scope.editUser.id) {
                    userService.updateUser($scope.editUser)
                        .then(function onSuccess(response) {
                            $scope.editUser = response.data;
                            Notification.success($scope.getI18nMessage('users.messages.UPDATE_CONFIRMATION'));
                        }).catch(function onError(response) {
                        $scope.handleErrorNotification(response);
                    });
                } else {
                    userService.createUser($scope.editUser)
                        .then(function onSuccess(response) {
                            $scope.editUser = response.data;
                            Notification.success($scope.getI18nMessage('users.messages.CREATE_CONFIRMATION'));
                        }).catch(function onError(response) {
                        $scope.handleErrorNotification(response);
                    });
                }
            };

            $scope.languges = [
                {id: "en", label: "English"},
                {id: "ro", label: "Română"}
            ];

            $scope.editUserPrivileges = function () {
                $location.$$search = {};
                $location.path("/user-privileges/edit/" + $scope.editUser.id).search({isEdit: true});
            };
        }
    ]
);