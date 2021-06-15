'use strict';

describe('Unit test for usersController', function () {
    var $controller;

    beforeEach(module('lite'));

    beforeEach(inject(function (_$controller_) {
        $controller = _$controller_;
    }));

    describe('Check $scope.pageSize on init', function () {

        it('Check the $scope.pageSize value', function () {
            var $scope = {};
            var controller = $controller('usersController', {$scope: $scope});

            expect($scope.pageSize).toEqual(20);
        });
    });
});