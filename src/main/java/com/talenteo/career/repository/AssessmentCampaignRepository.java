package com.talenteo.career.repository;

import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.model.entity.embedded.AssessmentCampaignId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentCampaignRepository extends JpaRepository<AssessmentCampaign, AssessmentCampaignId> {

    /**
     * =======================================================
     * Find Campaign ordered by end date desc
     * =======================================================
     * @return
     */
    AssessmentCampaign findFirstByOrderByEndDateDesc();

    //@Query("Select ac from AssessmentCampaign ac where ac.id.company_id=:companyId")
    //List<AssessmentCampaign> findByCompanyId(Long companyId);

    List<AssessmentCampaign> findByIdCompanyId(Long companyId);
}
