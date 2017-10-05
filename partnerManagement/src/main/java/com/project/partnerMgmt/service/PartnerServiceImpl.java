package com.project.partnerMgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.partnerMgmt.model.Partner;
import com.project.partnerMgmt.repository.PartnerRepository;

@Service("partnerService")
public class PartnerServiceImpl implements PartnerService{
	@Autowired
	private PartnerRepository partnerRepository;

	public List<Partner> findAllPartners() {
		List<Partner> partners = new ArrayList<>();
		partnerRepository.findAll()
		.forEach(partners::add);
		return partners;
	}
	
	public Partner findById(long id) {
		return partnerRepository.findOne(id);
	}
	
	public void savePartner(Partner partner) {
		partnerRepository.save(partner);
	}

	public void updatePartner(Partner partner) {
		partnerRepository.save(partner);
	}

	public void deletePartnerById(long id) {
		partnerRepository.delete(id);
	}
}
