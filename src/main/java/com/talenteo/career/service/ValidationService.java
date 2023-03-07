package com.talenteo.career.service;

import com.easyms.rest.ms.rest.Validator;
import com.talenteo.career.dto.BiannualAssessmentRequestDto;
import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.model.entity.Career;
import com.talenteo.career.model.entity.embedded.AssessmentCampaignId;
import com.talenteo.career.model.entity.embedded.BiannualAssessmentId;
import com.talenteo.career.client.HrMsClient;
import com.talenteo.career.repository.BiannualAssessmentRepository;
import com.talenteo.career.utils.CareerMessages;
import com.talenteo.common.error.TalenteoCommonMessages;
import com.talenteo.hr.dto.HumanResourceDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
@AllArgsConstructor
@Transactional
@Slf4j
public class ValidationService {

    private final BiannualAssessmentRepository biannualAssessmentRepository;
    private final HrMsClient hrMsClient;

    /**
     * =======================================================
     * Validate BiAnnual Assessment Request
     * =======================================================
     * @param request
     */
    public void validate(BiannualAssessmentRequestDto request) {
        Validator.of(request)
                .validateIf(this::validateCompositeId, () -> new IllegalStateException(CareerMessages.id_not_valid.getErrorKey()))
                .ifValid()
                .validateIf(this::existingCompositeId, () -> new IllegalStateException((CareerMessages.biannual_assessment_already_exist.getErrorKey())))
                .execute();
    }

    /**
     * =======================================================
     * Test if composite Id exists
     * =======================================================
     * @param requestDto
     * @return
     */
    private boolean existingCompositeId(BiannualAssessmentRequestDto requestDto) {
        return biannualAssessmentRepository.findById(getCompositeKey(requestDto)).isPresent();
    }

    /**
     * =======================================================
     * validate BiannualAssessment CompositeId
     * =======================================================
     * @param assessmentRequestDto
     * @return
     */

    private boolean validateCompositeId(BiannualAssessmentRequestDto assessmentRequestDto) {
        return Objects.isNull(assessmentRequestDto.getCareer())
                || Objects.isNull(assessmentRequestDto.getYear())
                || Objects.isNull(assessmentRequestDto.getSemester());
    }

    /**
     * =======================================================
     * Validate resource
     * =======================================================
     * @param id
     */
    public void validateResource(Long id) throws Exception{

        ResponseEntity<HumanResourceDto> hrDto = hrMsClient.getById(id);

        if (hrDto.getStatusCodeValue()==404)
            throw new IllegalStateException(TalenteoCommonMessages.hr_not_found.getErrorKey());

    }

    /**
     * =======================================================
     * get CompositeId of Biannual assessment request
     * =======================================================
     * @param requestDto
     * @return
     */

    private BiannualAssessmentId getCompositeKey(BiannualAssessmentRequestDto requestDto) {
        return BiannualAssessmentId.builder()
                .assessmentCampaign(AssessmentCampaign.builder()
                        .id(AssessmentCampaignId.builder()
                                .semester(requestDto.getSemester())
                                .year(requestDto.getYear())
                                .build())
                        .build())
                .career(Career.builder()
                        .id(requestDto.getCareer())
                        .build())
                .build();
    }
}
