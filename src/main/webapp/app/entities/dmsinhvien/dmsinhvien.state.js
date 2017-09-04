(function() {
    'use strict';

    angular
        .module('demomysqlApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dmsinhvien', {
            parent: 'entity',
            url: '/dmsinhvien',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demomysqlApp.dmsinhvien.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dmsinhvien/dmsinhviens.html',
                    controller: 'DmsinhvienController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dmsinhvien');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dmsinhvien-detail', {
            parent: 'dmsinhvien',
            url: '/dmsinhvien/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demomysqlApp.dmsinhvien.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dmsinhvien/dmsinhvien-detail.html',
                    controller: 'DmsinhvienDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dmsinhvien');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Dmsinhvien', function($stateParams, Dmsinhvien) {
                    return Dmsinhvien.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dmsinhvien',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dmsinhvien-detail.edit', {
            parent: 'dmsinhvien-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dmsinhvien/dmsinhvien-dialog.html',
                    controller: 'DmsinhvienDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dmsinhvien', function(Dmsinhvien) {
                            return Dmsinhvien.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dmsinhvien.new', {
            parent: 'dmsinhvien',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dmsinhvien/dmsinhvien-dialog.html',
                    controller: 'DmsinhvienDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ma: null,
                                ten: null,
                                ngaysinh: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dmsinhvien', null, { reload: 'dmsinhvien' });
                }, function() {
                    $state.go('dmsinhvien');
                });
            }]
        })
        .state('dmsinhvien.edit', {
            parent: 'dmsinhvien',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dmsinhvien/dmsinhvien-dialog.html',
                    controller: 'DmsinhvienDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dmsinhvien', function(Dmsinhvien) {
                            return Dmsinhvien.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dmsinhvien', null, { reload: 'dmsinhvien' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dmsinhvien.delete', {
            parent: 'dmsinhvien',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dmsinhvien/dmsinhvien-delete-dialog.html',
                    controller: 'DmsinhvienDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dmsinhvien', function(Dmsinhvien) {
                            return Dmsinhvien.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dmsinhvien', null, { reload: 'dmsinhvien' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
