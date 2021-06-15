angular
    .module('lite')
    .component('menuForMobileComponent', {

        controller: controller,
        templateUrl: 'app/components/menu-for-mobile-component/menu-for-mobile-component.html',
        transclude: false,
        controllerAs: 'MenuForMobileController',
        bindings: {
            checkIfMobileDevice: '<',
            navigationItems: '<',
            privileges: '<',
            toggleMenuForMobile: '&',
            showMenuForMobile: '<'
        }
    });

function controller($scope, $compile, $attrs) {
    var thisController = this;

    this.toggleThisMenu = function () {
        this.toggleMenuForMobile();
    };

    this.preventDefault = function (e) {
        e.preventDefault();
    };

    this.$onInit = function () {
        console.log('user agent: ', '\n', navigator.userAgent);
        thisController.isMobileDevice = !!this.checkIfMobileDevice.any;
    }
}
