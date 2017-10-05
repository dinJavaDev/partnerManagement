package com.project.partnerMgmt.service;

import java.util.List;

import com.project.partnerMgmt.model.Partner;



public interface PartnerService{
	
	Partner findById(long id);
	
	void savePartner(Partner partner);
	
	void updatePartner(Partner partner);
	
	void deletePartnerById(long id);

	List<Partner> findAllPartners(); 
	
}
