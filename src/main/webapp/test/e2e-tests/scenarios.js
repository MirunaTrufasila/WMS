'use strict';

/* https://github.com/angular/protractor/blob/master/docs/toc.md */
// popao - change tests
describe('my app', function () {


    it('should automatically redirect to / when location hash/fragment is empty', function () {
        browser.get('index.html');
        expect(browser.getLocationAbsUrl()).toMatch("/");
    });


    describe('index', function () {

        beforeEach(function () {
            browser.get('index.html');
        });


        it('should render index when user navigates to /', function () {
            expect(element.all(by.css('[ng-view] p')).first().getText()).toMatch(/partial for index/);
        });

    });

});
