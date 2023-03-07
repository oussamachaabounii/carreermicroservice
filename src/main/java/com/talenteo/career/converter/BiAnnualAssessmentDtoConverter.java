package com.talenteo.career.converter;


import com.talenteo.career.dto.BiAnnualAssessmentDto;

import com.talenteo.career.dto.BiAnnualAssessmentStatus;
import com.talenteo.career.model.entity.BiannualAssessment;
import com.talenteo.td.dto.TechnicalDocumentDto;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@Data(staticConstructor = "newInstance")
public class BiAnnualAssessmentDtoConverter implements Converter<BiannualAssessment, BiAnnualAssessmentDto> {
    private TechnicalDocumentDto technicalDocumentDto;

    @Override
    public BiAnnualAssessmentDto convert(BiannualAssessment biannualAssessment) {
        if (Objects.isNull(biannualAssessment)) {
            return null;
        }
        return BiAnnualAssessmentDto.builder()
                .semester(biannualAssessment.getId().getAssessmentCampaign().getId().getSemester())
                .year(biannualAssessment.getId().getAssessmentCampaign().getId().getYear())
                .careerId(biannualAssessment.getId().getCareer().getId())
                .companyId(biannualAssessment.getId().getAssessmentCampaign().getId().getCompanyId())
                .comment(biannualAssessment.getComment())
                .corporate(biannualAssessment.getCorporate())
                .creationDate(biannualAssessment.getCreationDate())
                .customerRelationship(biannualAssessment.getCustomerRelationship())
                .functionalIndustryCompetency(biannualAssessment.getFunctionalIndustryCompetency())
                .internalTrainerPotential(biannualAssessment.getInternalTrainerPotential())
                .lastUpdate(biannualAssessment.getLastUpdate())
                .managementAbility(biannualAssessment.getManagementAbility())
                .organisationalSkill(biannualAssessment.getOrganisationalSkill())
                .sfEvolutivity(biannualAssessment.getSfEvolutivity())
                .bmEvolutivity(biannualAssessment.getBmEvolutivity())
                .score(biannualAssessment.getScore())
                .status(BiAnnualAssessmentStatus.valueOf(biannualAssessment.getStatus()))
                .globalPerformanceRating(biannualAssessment.getGlobalPerformanceRating())
                .teamSpirit(biannualAssessment.getTeamSpirit())
                .userUpdate(biannualAssessment.getUserUpdate())
                .version(biannualAssessment.getVersion())
                .technicalSkill(biannualAssessment.getTechnicalSkill())
                .customerRelationshipComment(biannualAssessment.getCustomerRelationshipComment())
                .functionalIndustryCompetencyComment(biannualAssessment.getFunctionalIndustryCompetencyComment())
                .managementAbilityComment(biannualAssessment.getManagementAbilityComment())
                .organisationalSkillComment(biannualAssessment.getOrganisationalSkillComment())
                .corporateComment(biannualAssessment.getCorporateComment())
                .technicalSkillComment(biannualAssessment.getTechnicalSkillComment())
                .teamSpiritComment(biannualAssessment.getTeamSpiritComment())
                .attendance(biannualAssessment.getAttendance())
                .attendanceComment(biannualAssessment.getAttendanceComment())
                .deliverableQuality(biannualAssessment.getDeliverableQuality())
                .deliverableQualityComment(biannualAssessment.getDeliverableQualityComment())
                .build();
    }

    public BiAnnualAssessmentDtoConverter withTd(TechnicalDocumentDto technicalDocumentDto) {
        this.technicalDocumentDto = technicalDocumentDto;
        return this;
    }


}
