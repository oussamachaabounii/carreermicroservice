package com.talenteo.career.repository;



import com.talenteo.career.model.entity.embedded.AssessmentCampaignId;
import com.talenteo.career.model.entity.BiannualAssessment;
import com.talenteo.career.model.entity.embedded.BiannualAssessmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BiannualAssessmentRepository extends JpaRepository<BiannualAssessment, BiannualAssessmentId> {

    List<BiannualAssessment> findByIdAssessmentCampaignId(AssessmentCampaignId assessmentCampaignId);

    @Query("select u from BiannualAssessment u " +
            "join AssessmentCampaign ac on (u.id.assessmentCampaign.id = ac.id and ac.id.semester = ?1 and ac.id.year = ?2) " +
            "where u.id.career.id = ?3")
    Optional<BiannualAssessment> findByAssessmentCampaignAndCareer(Integer semester, Integer year, Long career);

    List<BiannualAssessment> findByIdCareerHr(long hrId);

    List<BiannualAssessment> findByIdCareerCm(long cmId);

    @Query("select u from BiannualAssessment u where u.id.career.id = ?1")
    List<BiannualAssessment> findByIdCareer(long careerId);

    @Query("select u from BiannualAssessment u where u.id.career.id = ?1")
    List<BiannualAssessment> findByCareerId(long careerId);


}
