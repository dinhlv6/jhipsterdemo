(function() {
    'use strict';

    angular
        .module('demomysqlApp')
        .controller('DmsinhvienController', DmsinhvienController);

    DmsinhvienController.$inject = ['Dmsinhvien'];

    function DmsinhvienController(Dmsinhvien) {

        var vm = this;

        vm.dmsinhviens = [];

        loadAll();

        function loadAll() {
            Dmsinhvien.query(function(result) {
                vm.dmsinhviens = result;
                vm.searchQuery = null;
            });
        }
    }
})();
