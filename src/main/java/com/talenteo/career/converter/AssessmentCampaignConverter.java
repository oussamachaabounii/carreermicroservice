package com.talenteo.career.converter;

import com.talenteo.career.dto.AssessmentCampaignRequestDto;
import com.talenteo.career.dto.BiannualAssessmentRequestDto;
import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.model.entity.BiannualAssessment;
import com.talenteo.career.model.entity.Career;
import com.talenteo.career.model.entity.embedded.AssessmentCampaignId;
import com.talenteo.career.model.entity.embedded.BiannualAssessmentId;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
@Data(staticConstructor = "newInstance")
public class AssessmentCampaignConverter implements Converter<AssessmentCampaignRequestDto, AssessmentCampaign> {

    @Override
    public AssessmentCampaign convert(AssessmentCampaignRequestDto input) {
        if (Objects.isNull(input)) {
            return null;
        }
        AssessmentCampaignId ac = AssessmentCampaignId.builder()
                .semester(input.getSemester())
                .year(input.getYear())
                .companyId(input.getCompanyId())
                .build();

        return AssessmentCampaign.builder().id(ac)
                .endDate(input.getEndDate())
                .build();




    }

}
