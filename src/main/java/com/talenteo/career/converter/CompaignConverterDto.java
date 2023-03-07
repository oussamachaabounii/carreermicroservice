package com.talenteo.career.converter;


import com.talenteo.career.dto.AssessmentCampaignDto;
import com.talenteo.career.model.entity.AssessmentCampaign;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@Data(staticConstructor = "newInstance")
public class CompaignConverterDto implements Converter<AssessmentCampaign, AssessmentCampaignDto> {
    @Override
    public AssessmentCampaignDto convert(AssessmentCampaign input) {
        if (Objects.isNull(input)) {
            return null;
        }
        return AssessmentCampaignDto.builder()
                .semester(input.getId().getSemester())
                .year(input.getId().getYear())
                .companyId(input.getId().getCompanyId())
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .adminEndDate(input.getAdminEndDate())
                .build();
    }
}
