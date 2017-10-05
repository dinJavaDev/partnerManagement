package com.project.partnerMgmt.repository;

import com.project.partnerMgmt.model.Partner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartnerRepository extends MongoRepository<Partner, Long> {
}
