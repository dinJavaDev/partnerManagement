package com.project.partnerMgmt.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.partnerMgmt.model.Partner;
import com.project.partnerMgmt.service.PartnerService;
 
@RestController
public class DashboardRestController {
 
    @Autowired
    private PartnerService partnerService;  //Service which will do all data retrieval/manipulation work
    private final String RSC_BASE_URI = "/partners/";
    private final String RSC_URI_WITH_PARAM = RSC_BASE_URI+"{id}";
    
    //-------------------Retrieve All Partners--------------------------------------------------------
     
    @RequestMapping(value = RSC_BASE_URI, method = RequestMethod.GET)
    public ResponseEntity<List<Partner>> listAllPartners() {
        List<Partner> partners = partnerService.findAllPartners();
        if(partners.isEmpty()){
            return new ResponseEntity<List<Partner>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Partner>>(partners, HttpStatus.OK);
    }
 
 
    
    //-------------------Retrieve Single Partner--------------------------------------------------------
     
    @RequestMapping(value = RSC_URI_WITH_PARAM, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Partner> getPartner(@PathVariable("id") long id) {
        Partner partner = partnerService.findById(id);
        if (partner == null) {
            return new ResponseEntity<Partner>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Partner>(partner, HttpStatus.OK);
    }
 
    //-------------------Create a Partner--------------------------------------------------------
     
    @RequestMapping(value = RSC_BASE_URI, method = RequestMethod.POST)
    public ResponseEntity<Void> createPartner(@RequestBody Partner partner, UriComponentsBuilder ucBuilder) {
        partnerService.savePartner(partner);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/partners/{id}").buildAndExpand(partner.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
    
     
    //------------------- Update a Partner --------------------------------------------------------
     
    @RequestMapping(value = RSC_URI_WITH_PARAM, method = RequestMethod.PUT)
    public ResponseEntity<Partner> updatePartner(@PathVariable("id") long id, @RequestBody Partner partner) {
        Partner currentPartner = partnerService.findById(id);
         
        if (currentPartner==null) {
            return new ResponseEntity<Partner>(HttpStatus.NOT_FOUND);
        }
 
        currentPartner.setPartnerName(partner.getPartnerName());
        currentPartner.setAddress(partner.getAddress());
        currentPartner.setEmail(partner.getEmail());
         
        partnerService.updatePartner(currentPartner);
        return new ResponseEntity<Partner>(currentPartner, HttpStatus.OK);
    }
 
    
    
    //------------------- Delete a Partner --------------------------------------------------------
     
    @RequestMapping(value = RSC_URI_WITH_PARAM, method = RequestMethod.DELETE)
    public ResponseEntity<Partner> deletePartner(@PathVariable("id") long id) {
 
        Partner partner = partnerService.findById(id);
        if (partner == null) {
            return new ResponseEntity<Partner>(HttpStatus.NOT_FOUND);
        }
 
        partnerService.deletePartnerById(id);
        return new ResponseEntity<Partner>(HttpStatus.NO_CONTENT);
    }
}