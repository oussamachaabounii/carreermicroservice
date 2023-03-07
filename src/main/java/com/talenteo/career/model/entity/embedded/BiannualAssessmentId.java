package com.talenteo.career.model.entity.embedded;

import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.model.entity.Career;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Data
@Embeddable
@Builder
public class BiannualAssessmentId implements Serializable {

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "year", referencedColumnName = "year"),
            @JoinColumn(name = "semester", referencedColumnName = "semester"),
            @JoinColumn(name = "company_id", referencedColumnName = "company_id")})
    private AssessmentCampaign assessmentCampaign;

    @ManyToOne
    @JoinColumn(name = "career_id")
    private Career career;
}
