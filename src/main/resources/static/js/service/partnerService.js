'use strict';

angular.module('partnerApp').factory('PartnerService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/partners/';

    var factory = {
        fetchAllPartners: fetchAllPartners,
        createPartner: createPartner,
        updatePartner:updatePartner,
        deletePartner:deletePartner
    };

    return factory;

    function fetchAllPartners() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Partners');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function createPartner(partner) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, partner)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating Partner');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function updatePartner(partner, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, partner)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating Partner');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deletePartner(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting Partner');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
