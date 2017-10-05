package com.project.partnerMgmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.partnerMgmt.model.Partner;
import com.project.partnerMgmt.repository.PartnerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartnerServiceTest{
	@Autowired
	private PartnerRepository partnerRepository;
	private final long testPartnerId=99;
	
	@Test
	public void testFindAllPartners() {
		List<Partner> partners = new ArrayList<>();
		partnerRepository.findAll()
		.forEach(partners::add);
		assertNotNull("Failure - Expected not null", partners);
	}

	@Test
	public void testSavePartner() {
		Partner newPartner = new Partner(testPartnerId,"partnerTestName","PartnerTestAdress","partneremail@email.com");
		partnerRepository.save(newPartner);
		Partner partner = partnerRepository.findOne(newPartner.getId());
		assertNotNull("Failure - Expected not null", partner);
		assertEquals("Failure - Expected partner id ", newPartner.getId(), partner.getId());
	}

	@Test
	public void testFindById() {
		Partner newPartner = new Partner(testPartnerId,"partnerTestName","PartnerTestAdress","partneremail@email.com");
		partnerRepository.save(newPartner);
		Partner partner = partnerRepository.findOne(testPartnerId);
		assertNotNull("Failure - Expected not null", partner);
		assertEquals("Failure - Expected partner id ", testPartnerId, partner.getId());
	}

	@Test
	public void testFindByIdNotFound() {
		long id=99999;
		Partner partner = partnerRepository.findOne(id);
		assertNull("Failure - Expected null", partner);
	}
	

	@Test
	public void testUpdatePartner() {
		Partner partner=new Partner(testPartnerId,"partnerTestName","PartnerTestUpdatedAdress","partnerUpdateEmail@email.com");
		partnerRepository.save(partner);
		Partner fetchedPartner = partnerRepository.findOne(partner.getId());
		assertNotNull("Failure - Expected not null", fetchedPartner);
		assertEquals("Failure - Expected partner id ", partner.getAddress(), fetchedPartner.getAddress());
	}

	@Test
	public void testDeletePartnerById() {
		partnerRepository.delete(testPartnerId);
		Partner partner = partnerRepository.findOne(testPartnerId);
		assertNull("Failure - Expected null", partner);
	}
	
	@After
	public void tearDown()
	{
		partnerRepository.delete(testPartnerId);	
	}
}
