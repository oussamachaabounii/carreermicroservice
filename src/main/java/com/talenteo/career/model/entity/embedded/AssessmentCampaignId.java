package com.talenteo.career.model.entity.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Data
@Embeddable
@Builder
public class AssessmentCampaignId implements Serializable {

    Integer semester;
    Integer year;
    @Column(name = "company_id")
    Long companyId;
}
