angular.module('lite').controller('userPrivilegesController',
    ['$scope', 'userPrivilegesService', 'uiGridConstants', 'Notification', '$location', 'resolvedUser', '$routeParams',
        'resolvedUserPrivileges',
        function ($scope, userPrivilegesService, uiGridConstants, Notification, $location, resolvedUser, $routeParams,
                  resolvedUserPrivileges) {

            $scope.editUser = resolvedUser;
            $scope.privilegesTreeData = resolvedUserPrivileges;
            $scope.selectedPrivilegesCount = 0;

            $scope.back = function () {
                $location.$$search = {};
                if ($routeParams.isEdit) {
                    $location.path("/users/edit/" + $scope.editUser.id);
                } else {
                    $location.path("/users");
                }
            };

            $scope.checkAll = function () {
                $('#userPrivilegesTree').treeview('checkAll', {silent: true});
                $scope.selectedPrivilegesCount = $('#userPrivilegesTree').treeview('getChecked').length;

            };

            $scope.uncheckAll = function () {
                $('#userPrivilegesTree').treeview('uncheckAll', {silent: true});
                $scope.selectedPrivilegesCount = 0;
            };

            $scope.save = function () {
                var selectedNodes = $('#userPrivilegesTree').treeview('getChecked');
                var privileges = [];
                angular.forEach(selectedNodes, function (node) {
                    privileges.push(node.id);
                });
                userPrivilegesService.update($scope.editUser.id, privileges)
                    .then(function onSuccess(response) {
                        Notification.success($scope.getI18nMessage('users-privileges.messages.UPDATE_CONFIRMATION'));
                    }).catch(function onError(response) {
                    $scope.handleErrorNotification(response);
                });
            };

            $scope.userCheckStateChanged = function (node) {
                var tree = $('#userPrivilegesTree');
                $scope.setParentStateOnChildren(node, tree);
                $scope.checkParents(node, tree);
                $scope.selectedPrivilegesCount = tree.treeview('getChecked').length;
            };

            $scope.treeOptions = {
                bootstrap2: false,
                showTags: true,
                width: 500,
                levels: 0,
                silent: true,
                showCheckbox: true,
                onNodeChecked: function (event, node) {
                    $scope.userCheckStateChanged(node);
                },
                onNodeUnchecked: function (event, node) {
                    $scope.userCheckStateChanged(node);
                },
                onNodeSelected: function (event, node) {
                    $('#userPrivilegesTree').treeview('toggleNodeChecked', [node.nodeId]);
                }
            };

            $scope.buildUserPrivilegesTree = function () {
                $scope.treeOptions.data = $scope.privilegesTreeData;
                $('#userPrivilegesTree').treeview($scope.treeOptions);
                $scope.selectedPrivilegesCount = $('#userPrivilegesTree').treeview('getChecked').length;
            };
        }
    ]
);