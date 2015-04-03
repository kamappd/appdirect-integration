'use strict';

angular.module('app.controllers')
  .run(function ($websocket) {

  })
  .controller('eventsCtrl', ['$scope', '$websocket',
    function ($scope, $websocket) {
      $scope.events = [];


      var ws = $websocket.$new('ws://localhost:12345/topic'); // instance of ngWebsocket, handled by $websocket service

      ws.open();

      ws.$on('$open', function () {
        console.log('Oh my gosh, websocket is really open! Fukken awesome!');

        ws.$emit('app/events');
      });

      ws.$on('events', function (data) {
        $scope.events = data;
        //ws.$close();
      });

      ws.$on('$close', function () {
        console.log('Noooooooooou, I want to have more fun with ngWebsocket, damn it!');
      });

    }]
);
