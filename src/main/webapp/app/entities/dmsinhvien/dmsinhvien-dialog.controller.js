(function() {
    'use strict';

    angular
        .module('demomysqlApp')
        .controller('DmsinhvienDialogController', DmsinhvienDialogController);

    DmsinhvienDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Dmsinhvien'];

    function DmsinhvienDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Dmsinhvien) {
        var vm = this;

        vm.dmsinhvien = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dmsinhvien.id !== null) {
                Dmsinhvien.update(vm.dmsinhvien, onSaveSuccess, onSaveError);
            } else {
                Dmsinhvien.save(vm.dmsinhvien, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demomysqlApp:dmsinhvienUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.ngaysinh = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
