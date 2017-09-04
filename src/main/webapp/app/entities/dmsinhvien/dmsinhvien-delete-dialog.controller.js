(function() {
    'use strict';

    angular
        .module('demomysqlApp')
        .controller('DmsinhvienDeleteController',DmsinhvienDeleteController);

    DmsinhvienDeleteController.$inject = ['$uibModalInstance', 'entity', 'Dmsinhvien'];

    function DmsinhvienDeleteController($uibModalInstance, entity, Dmsinhvien) {
        var vm = this;

        vm.dmsinhvien = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Dmsinhvien.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
