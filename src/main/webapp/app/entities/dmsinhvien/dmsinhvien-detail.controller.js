(function() {
    'use strict';

    angular
        .module('demomysqlApp')
        .controller('DmsinhvienDetailController', DmsinhvienDetailController);

    DmsinhvienDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Dmsinhvien'];

    function DmsinhvienDetailController($scope, $rootScope, $stateParams, previousState, entity, Dmsinhvien) {
        var vm = this;

        vm.dmsinhvien = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('demomysqlApp:dmsinhvienUpdate', function(event, result) {
            vm.dmsinhvien = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
