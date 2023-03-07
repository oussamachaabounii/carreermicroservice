package com.talenteo.career.converter;


import com.talenteo.career.dto.CareerMobilityDto;
import com.talenteo.career.model.entity.CareerMobility;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

@Data(staticConstructor = "newInstance")
public class CareerMobilityConverter implements Converter<CareerMobility, CareerMobilityDto> {
    @Override
    public CareerMobilityDto convert(CareerMobility careerMobility) {
        return CareerMobilityDto.builder()
                .mobilityCode(careerMobility.getId().getMobilityCode())
                .build();
    }
}
