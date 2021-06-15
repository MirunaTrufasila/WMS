# [ng-breadcrumbs](http://desirable-objects.github.io/ng-breadcrumbs/)
*AngularJS Breadcrumbs for ngRoute*

This project was built using [ng-boilerplate](https://github.com/ianwalter/ng-boilerplate)!
This project was forked from the abandoned ng-breadcrumbs module by [Ian Von Walter](http://ianvonwalter.com)
It was then maintained briefly by a number of contributors including [shoshi](https://github.com/shoshi/angular-breadcrumbs)
It has been given a couple of new features.

Works with Angular 1.5.x!

#### Step 1: Install ng-breadcrumbs

Install using npm (preferred):

```
npm install --save angular-breadcrumbs
require('angular-breadcrumbs') // For webpack
```

Install using Bower:

```
bower install angular-breadcrumbs --save
```

Include ng-breadcrumbs.min.js in your app.

#### Step 2: Set up routing

In order to use breadcrumbs you'll need to use configure your app to use Angular's routeProvider. You'll also need to
load the ng-breadcrumbs module. You can then set a label for each route (breadcrumb) within the route options.

```javascript
  var app = angular.module('ab', ['ngRoute', 'ng-breadcrumbs'])
    .config(['$routeProvider', function($routeProvider) {
      $routeProvider
        .when('/', { templateUrl: 'assets/template/home.html', label: 'Home' })
        .when('/stock/:stock', { controller: 'StockController', templateUrl: 'assets/template/stock.html' })
        .when('/stock/:stock/detail', {
          controller: 'StockDetailController',
          templateUrl: 'assets/template/stock-detail.html',
          label: 'More Detail'
        })
        .otherwise({ redirectTo: '/' });
```


#### Step 3: Make the breadcrumbs service available to your controller

Set the breadcrumbs service in your app's main controller.

```javascript
  app.controller('HomeController', [
    '$scope',
    'breadcrumbs',
    function($scope, breadcrumbs) {
      $scope.breadcrumbs = breadcrumbs;
      ...
```


#### Step 4: Display the breadcrumbs within your app

This HTML snippet will display your breadcrumb navigation and leave the last breadcrumb (the page you're currently on)
unlinked.

```html
  <ol class="ab-nav breadcrumb">
    <li ng-repeat="breadcrumb in breadcrumbs.get() track by breadcrumb.path" ng-class="{ active: $last }">
      <a ng-if="!$last" ng-href="#{{ breadcrumb.path }}" ng-bind="breadcrumb.label" class="margin-right-xs"></a>
      <span ng-if="$last" ng-bind="breadcrumb.label"></span>
    </li>
  </ol>
```

That's it! You should now have breadcrumb navigation that can even handle nested routes.


#### Adding dynamic route labels

To add dynamic route labels, create an options object on the breadcrumbs service or pass one as a parameter within
```breadcrumbs.get()```, for example:

```javascript
// Will replace the default label 'Stock Detail' with the dynamic label 'AAPL Details'
breadcrumbs.options = { 'Stock Detail': $routeParams.stock + ' Details' };
```

#### Adding arbitrary options to a route

To add extra configuration to your route, simply define 'options' in your route definition, i.e:

```javascript
// Will be available as breadcrumbs.get()[index].options.hidden
.when('/', { templateUrl: 'assets/template/home.html', label: 'Home', options: {hidden: false})
```
