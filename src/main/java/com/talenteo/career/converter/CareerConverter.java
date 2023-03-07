package com.talenteo.career.converter;


import com.talenteo.career.dto.CareerDto;
import com.talenteo.career.model.entity.Career;
import com.talenteo.career.model.entity.CareerMobility;
import com.talenteo.career.model.entity.embedded.CareerMobilityId;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
import java.util.stream.Collectors;

@Data(staticConstructor = "newInstance")
public class CareerConverter implements Converter<Career, CareerDto> {

    @Override
    public CareerDto convert(Career career) {
        if (Objects.isNull(career)) {
            return null;
        }
        return CareerDto.builder()
                .id(career.getId())
                .hr(career.getHr())
                .comment(career.getComment())
                .availabilityDate(career.getAvailabilityDate())
                //.biannualAssessments(career.getBiannualAssessments().stream().map(BiAnnualAssessmentDtoConverter.newInstance()::convert).collect(Collectors.toList()))
                //.careerMobilities(career.getCareerMobilities().stream().map(CareerMobility::getId).map(CareerMobilityId::getMobilityCode).collect(Collectors.toList()))
                .cm(career.getCm())
                .cma(career.getCma())
                .dailyFee(career.getDailyFee())
                .devise(career.getDevise())
                .executiveStatus(career.getExecutiveStatus())
                .lastBa(career.getLastBa())
                .monthlySalary(career.getMonthlySalary())
                .mobilityComment(career.getMobilityComment())
                .lastBmFollowup(null)
                .entryDate(career.getEntryDate())
                .build();
    }
}
