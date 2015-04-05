'use strict';

angular.module('app.controllers', []);
var app = angular.module('app', [
  //'app.controllers',
  'ngRoute'
]);


app.controller('EventsController', ['$scope', '$location', '$http',
    function ($scope, $location, $http) {
      $scope.subscriptions = [];
      $scope.events = [];

      $http.get('/events')
        .success(function(data) {
          $scope.events = data;
        });

      $http.get('/subscriptions')
        .success(function(data) {
          $scope.subscriptions = data;
        });

      //console.log("toto");
      //var host = 'ws://' + $location.host() + '/events';
      //var ws = $websocket.$new(host);
      //
      //ws.$on('$open', function () {
      //  console.log('Oh my gosh, websocket is really open! Fukken awesome!');
      //});
      //
      //ws.$on('/topic/events', function (data) {
      //  $scope.events = data;
      //  //ws.$close();
      //});
      //
      //ws.$on('$close', function () {
      //  console.log('Noooooooooou, I want to have more fun with ngWebsocket, damn it!');
      //});

    }]
);
//.config(['$routeProvider', function ($routeProvider) {
//  $routeProvider
//    .when('/', {
//      controller: 'eventsCtrl'
//    })
//    .otherwise({
//      redirectTo: '/'
//    });
//}]);
