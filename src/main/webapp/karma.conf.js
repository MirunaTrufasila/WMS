// Karma configuration
// Generated on Mon Jun 22 2020 11:19:49 GMT+0300 (EEST)

module.exports = function (config) {
    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        // basePath: '',
        basePath: '../webapp/app/components/',


        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine'],


        // list of files / patterns to load in the browser
        files: [
            //jQuery
            '../../node_modules/jquery/dist/jquery.js',

            // angular & angular-mocks
            '../../node_modules/angular/angular.js',
            '../../node_modules/angular-mocks/angular-mocks.js',
            '../../node_modules/angular-stub-changes/index.js', // used for component testing by triggering changes lifecycle

            // angular related packages
            '../../node_modules/angular-**/dist/*.js',
            '../../node_modules/angular-route/angular-route.js',
            '../../node_modules/angular-ui-bootstrap/ui-bootstrap.js',
            '../../node_modules/angular-cookies/angular-cookies.js',
            '../../node_modules/angular-loading/angular-loading.js',
            '../../node_modules/angular-loading-bar/src/loading-bar.js',
            '../../node_modules/angular-animate/angular-animate.js',
            '../../node_modules/angular-sanitize/angular-sanitize.js',
            '../../node_modules/angular-material/angular-material.js',
            '../../node_modules/angular-aria/angular-aria.js',
            '../../node_modules/angular-messages/angular-messages.js',
            '../../node_modules/angular-breadcrumbs/dist/ng-breadcrumbs.js',
            '../../node_modules/angularjs-dropdown-multiselect/dist/angularjs-dropdown-multiselect.min.js',

            '../../node_modules/angular-recursion/angular-recursion.js',
            '../../node_modules/angular-uuid4/angular-uuid4.js',
            '../../node_modules/angular-drag-and-drop-lists/angular-drag-and-drop-lists.js',

            // angular-translate
            '../../node_modules/angular-translate/dist/angular-translate-*/*js',

            // local/custom resources
            '../../app/static/angular-tree-widget-master/dist/angular-tree-widget.js',
            '../../app/components/shared/directives/uiGridCell.js',


            // other deps
            '../../node_modules/isteven-angular-multiselect/isteven-multi-select.js',

            // angular-ui-grid
            '../../node_modules/angular-ui-grid/ui-grid.js',
            '../../node_modules/angular-ui-grid/ui-grid*.js',

            // bootstrap related packages
            '../../node_modules/bootstrap/dist/js/bootstrap.js',
            '../../node_modules/bootstrap-**/dist/*.js',
            '../../node_modules/bootstrap-**/dist/js/*.js',

            '../liteApp.js',
            '../../app/components/**/*.js',
            '../../app/components/**/**/*.js',
            '../../app/components/**/**/**/*.js',
            '../../app/components/**/**/**/**/*.js',
            '../../app/components/mainController.js',

            // actual tests
            '../../test/unit/*.js',
            '../../app/components/**/*spec.js',
            '../../app/components/**/**/*spec.js',
            '../../app/components/**/**/**/*spec.js',
            '../../app/components/**/**/**/**/*spec.js',
        ],


        // list of files to exclude
        exclude: [
            '../../node_modules/**/*Spec.js',
            '../../node_modules/**/*-spec.js',
            '../../node_modules/**/*_spec.js',
            '../../node_modules/**/*.spec.js',
            '../../node_modules/**/*.spec'
        ],


        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {},


        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress', 'summary'],

        // karma server reporting
        summaryReporter: {
            // 'failed', 'skipped' or 'all'
            show: 'all',
            // Limit the spec label to this length
            specLength: 50,
            // Show an 'all' column as a summary
            overviewColumn: true
        },


        // web server port
        port: 9876,


        // enable / disable colors in the output (reporters and logs)
        colors: true,


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,


        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,


        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['Chrome'],

        // If browser does not capture in given timeout [ms], kill it
        captureTimeout: 600000,


        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: false,

        // Concurrency level
        // how many browser should be started simultaneous
        concurrency: Infinity
    })
};
