package com.talenteo.career.converter;


import com.talenteo.career.dto.BiannualAssessmentSkillDto;
import com.talenteo.career.model.entity.BiannualAssessmentSkill;
import com.talenteo.career.model.entity.embedded.BiannualAssessmentSkillId;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
import java.util.Optional;

@Data(staticConstructor = "newInstance")
public class BiannualAssessmentSkillConverter implements Converter<BiannualAssessmentSkill, BiannualAssessmentSkillDto> {
    @Override
    public BiannualAssessmentSkillDto convert(BiannualAssessmentSkill biannualAssessmentSkill) {
        if (Objects.isNull(biannualAssessmentSkill)) {
            return null;
        }
        return BiannualAssessmentSkillDto.builder()
                .skillCode(Optional.of(biannualAssessmentSkill)
                        .map(BiannualAssessmentSkill::getId)
                        .map(BiannualAssessmentSkillId::getSkillCode)
                        .orElse(null))
                .rating(biannualAssessmentSkill.getRating())
                .build();
    }
}
