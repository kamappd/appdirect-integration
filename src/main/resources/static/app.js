'use strict';

angular.module('app.controllers', []);
angular.module('app', [
  'app.controllers',
  'ngWebsocket',
  'ngRoute'
]).config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    .when('/', {
      controller: 'eventsCtrl'
    })
    .otherwise({
      redirectTo: '/'
    });
}]);
