package com.talenteo.career.model.entity;

import com.talenteo.career.model.entity.embedded.AssessmentCampaignId;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "assessment_campaign")
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Data
@Builder
public class AssessmentCampaign {


    @EmbeddedId
    private AssessmentCampaignId id;

    private Date startDate;
    private Date endDate;
    private Date adminEndDate;

    @OneToMany(mappedBy = "id.assessmentCampaign")
    private List<BiannualAssessment> biannualAssessments;
}