(function() {
    'use strict';
    angular
        .module('demomysqlApp')
        .factory('Dmsinhvien', Dmsinhvien);

    Dmsinhvien.$inject = ['$resource', 'DateUtils'];

    function Dmsinhvien ($resource, DateUtils) {
        var resourceUrl =  'api/dmsinhviens/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.ngaysinh = DateUtils.convertLocalDateFromServer(data.ngaysinh);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.ngaysinh = DateUtils.convertLocalDateToServer(copy.ngaysinh);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.ngaysinh = DateUtils.convertLocalDateToServer(copy.ngaysinh);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
