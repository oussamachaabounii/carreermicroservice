package com.talenteo.career.converter;


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
public class BiannualAssessmentConverter implements Converter<BiannualAssessmentRequestDto, BiannualAssessment> {


    @Override
    public BiannualAssessment convert(BiannualAssessmentRequestDto biannualAssessmentRequestDto) {
        if (Objects.isNull(biannualAssessmentRequestDto)) {
            return null;
        }
        Career c = Career.builder().id(biannualAssessmentRequestDto.getCareer()).build();

        AssessmentCampaign ac = AssessmentCampaign.builder()
                .id(AssessmentCampaignId.builder().semester(biannualAssessmentRequestDto.getSemester()).year(biannualAssessmentRequestDto.getYear()).companyId(biannualAssessmentRequestDto.getCompanyId()).build())
                .build();

        return BiannualAssessment.builder()
                .id(BiannualAssessmentId.builder()
                        .assessmentCampaign(ac)
                        .career(c)
                        .build())
                .comment(biannualAssessmentRequestDto.getComment())
                .corporate(biannualAssessmentRequestDto.getCorporate())
                .creationDate(biannualAssessmentRequestDto.getCreationDate())
                .customerRelationship(biannualAssessmentRequestDto.getCustomerRelationship())
                .functionalIndustryCompetency(biannualAssessmentRequestDto.getFunctionalIndustryCompetency())
                .internalTrainerPotential(biannualAssessmentRequestDto.getInternalTrainerPotential())
                .lastUpdate(biannualAssessmentRequestDto.getLastUpdate())
                .organisationalSkill(biannualAssessmentRequestDto.getOrganisationalSkill())
                .sfEvolutivity(biannualAssessmentRequestDto.getSfEvolutivity())
                .bmEvolutivity(biannualAssessmentRequestDto.getBmEvolutivity())
                .score(biannualAssessmentRequestDto.getScore())
                .status(biannualAssessmentRequestDto.getStatus().name())
                .globalPerformanceRating(biannualAssessmentRequestDto.getGlobalPerformanceRating())
                .teamSpirit(biannualAssessmentRequestDto.getTeamSpirit())
                .userUpdate(biannualAssessmentRequestDto.getUserUpdate())
                .version(biannualAssessmentRequestDto.getVersion())
                .technicalSkill(biannualAssessmentRequestDto.getTechnicalSkill())
                .customerRelationshipComment(biannualAssessmentRequestDto.getCustomerRelationshipComment())
                .functionalIndustryCompetencyComment(biannualAssessmentRequestDto.getFunctionalIndustryCompetencyComment())
                .managementAbilityComment(biannualAssessmentRequestDto.getManagementAbilityComment())
                .organisationalSkillComment(biannualAssessmentRequestDto.getOrganisationalSkillComment())
                .corporateComment(biannualAssessmentRequestDto.getCorporateComment())
                .technicalSkillComment(biannualAssessmentRequestDto.getTechnicalSkillComment())
                .teamSpiritComment(biannualAssessmentRequestDto.getTeamSpiritComment())
                .managementAbility(biannualAssessmentRequestDto.getManagementAbility())
                .attendance(biannualAssessmentRequestDto.getAttendance())
                .attendanceComment(biannualAssessmentRequestDto.getAttendanceComment())
                .deliverableQuality(biannualAssessmentRequestDto.getDeliverableQuality())
                .deliverableQualityComment(biannualAssessmentRequestDto.getDeliverableQualityComment())
                .build();

    }


}
