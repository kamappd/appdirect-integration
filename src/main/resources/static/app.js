'use strict';

angular.module('app.controllers', []);
var app = angular.module('app', [
  //'app.controllers',
  'ngWebsocket',
  'ngRoute'
]);


app
  .controller('EventsController', ['$scope', '$websocket',
    function ($scope, $websocket) {
      $scope.events = [];
      $scope.toto = "toto";

      console.log("toto");
      var ws = $websocket.$new('ws://localhost:5000/events'); // instance of ngWebsocket, handled by $websocket service

      ws.open();

      ws.$on('$open', function () {
        console.log('Oh my gosh, websocket is really open! Fukken awesome!');
      });

      ws.$on('/topic/events', function (data) {
        $scope.events = data;
        //ws.$close();
      });

      ws.$on('$close', function () {
        console.log('Noooooooooou, I want to have more fun with ngWebsocket, damn it!');
      });

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
