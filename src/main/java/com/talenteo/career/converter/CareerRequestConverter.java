package com.talenteo.career.converter;


import com.talenteo.career.dto.BiannualAssessmentRequestDto;
import com.talenteo.career.dto.CareerRequestDto;
import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.model.entity.BiannualAssessment;
import com.talenteo.career.model.entity.Career;
import com.talenteo.career.model.entity.embedded.AssessmentCampaignId;
import com.talenteo.career.model.entity.embedded.BiannualAssessmentId;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@Data(staticConstructor = "newInstance")
public class CareerRequestConverter implements Converter<CareerRequestDto, Career> {


    @Override
    public Career convert(CareerRequestDto input) {
        if (Objects.isNull(input)) {
            return null;
        }


        return Career.builder()
               // .id()
                .comment(input.getComment())
                .hr(input.getHr())
                .cm(input.getCm())
                .entryDate(input.getEntryDate())
                .availabilityDate(input.getAvailabilityDate())
                .lastBa(input.getLastBa())
                .dailyFee(input.getDailyFee())
                .monthlySalary(input.getMonthlySalary())
                .executiveStatus(input.getExecutiveStatus())
                .build();

    }


}
