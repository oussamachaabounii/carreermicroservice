package com.talenteo.career.repository;


import com.talenteo.career.model.entity.CareerMobility;
import com.talenteo.career.model.entity.embedded.CareerMobilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobilityRepository extends JpaRepository<CareerMobility, CareerMobilityId> {

    List<CareerMobility> findByIdMobilityCodeIn(List<String> ids);

}
