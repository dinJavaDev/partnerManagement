'use strict';

angular.module('partnerApp').controller('PartnerController', ['$scope', 'PartnerService', function($scope, partnerService) {
    var self = this;
    self.partner={id:null,partnerName:'',address:'',email:''};
    self.partners=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    fetchAllPartners();

    function fetchAllPartners(){
    	partnerService.fetchAllPartners()
            .then(
            function(d) {
                self.partners = d;
            },
            function(errResponse){
                console.error('Error while fetching Partners');
            }
        );
    }

    function createPartner(partner){
        partnerService.createPartner(partner)
            .then(
            fetchAllPartners,
            function(errResponse){
                console.error('Error while creating Partner');
            }
        );
    }

    function updatePartner(partner, id){
        partnerService.updatePartner(partner, id)
            .then(
            fetchAllPartners,
            function(errResponse){
                console.error('Error while updating Partner');
            }
        );
    }

    function deletePartner(id){
        partnerService.deletePartner(id)
            .then(
            fetchAllPartners,
            function(errResponse){
                console.error('Error while deleting Partner');
            }
        );
    }

    function submit() {
        if($scope.updateFlag===false){
            console.log('Saving New Partner', self.partner);
            createPartner(self.partner);
        }else{
            updatePartner(self.partner, self.partner.id);
            console.log('Partner updated with id ', self.partner.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.partners.length; i++){
            if(self.partners[i].id === id) {
                self.partner = angular.copy(self.partners[i]);
                $scope.updateFlag = true;
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.partner.id === id) {//clean form if the partner to be deleted is shown there.
            reset();
        }
        deletePartner(id);
    }


    function reset(){
        self.partner={id:null,partnerName:'',address:'',email:''};
        $scope.myForm.$setPristine(); //reset Form
        $scope.updateFlag = false;
    }

}]);
