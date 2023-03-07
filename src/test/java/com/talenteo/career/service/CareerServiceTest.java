package com.talenteo.career.service;

import com.talenteo.career.CareerMsApplication;
import com.talenteo.career.client.TdMsClient;
import com.talenteo.career.dto.*;
import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.model.entity.BiannualAssessment;
import com.talenteo.career.model.entity.Career;
import com.talenteo.career.model.entity.embedded.BiannualAssessmentId;
import com.talenteo.career.repository.BiannualAssessmentRepository;
import com.talenteo.career.repository.CareerRepository;
import com.talenteo.td.dto.TechnicalDocumentDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = CareerMsApplication.class)

public class CareerServiceTest {

    @Inject
    CareerService careerService;

    @Inject
    CareerRepository careerRepository;

    @Inject
    private BiannualAssessmentRepository biannualAssessmentRepository;

    @MockBean
    private TdMsClient tdMsClient;


    @Test

    public void should_return_career_by_id() {
        Optional<CareerDto> career = careerService.findCareerById(2L);
        Assertions.assertThat(career).isNotEmpty();
    }

    @Test
    public void should_return_career_by_resource_id() {
        Optional<CareerDto> career = careerService.findCareerByResourceId(1L);
        Assertions.assertThat(career).isEmpty();
    }

    @Test
    public void should_return_biannual_assessment_by_id() {
        Optional<BiannualAssessment> career = biannualAssessmentRepository.findByAssessmentCampaignAndCareer(1, 2020, 1L);
        Assertions.assertThat(career).isEmpty();
    }

    @Test
    public void should_return_return_latest_assessment_campaign() {
        AssessmentCampaignDto latestAssessmentCampaign = careerService.findLatestAssessmentCampaign();
//        Assertions.assertThat(latestAssessmentCampaign).isNull();
        Assertions.assertThat(latestAssessmentCampaign.getStartDate()).isAfterYear(2019);
        Assertions.assertThat(latestAssessmentCampaign.getEndDate()).hasYear(2020).hasDayOfMonth(31).hasMonth(12);
        Assertions.assertThat(latestAssessmentCampaign.getEndDate()).hasYear(2020);

    }

    @Test
    public void should_return_history_of_assessments_by_hr_id() {
        doReturn(ResponseEntity.ok(TechnicalDocumentDto.builder().build())).
                when(tdMsClient).getByHrId(anyLong());
        List<BiAnnualAssessmentDto> assessments = careerService.findHistoryBiannualAssessmentsByResourceId(1L);
        Assertions.assertThat(assessments).isEmpty();


    }

    public void should_return_assessment_count_by_maneger() {

        BiannualAssessmentsCountDto assessmentsCountDto = careerService.countBiannualAssessments(2L);
        Assertions.assertThat(assessmentsCountDto).isNotNull();
    }

    @Test
    public void should_create_campaign() {
        AssessmentCampaignDto assessmentCampaignDto = careerService.saveAssessmentCampaign(getCampaign());
        Assertions.assertThat(assessmentCampaignDto).isNotNull();
    }


    @Test
    public void should_create_biannual_assessment() {
        BiAnnualAssessmentDto save = careerService.saveBiannualAssessment(getBiannualAssessment());
        Assertions.assertThat(save).isNotNull();
    }

    @Test
    public void should_return_assessment_count_for_CM() {

        BiannualAssessmentsCountDto countBiannualAssessments = careerService.countBiannualAssessments(1L);
        Assertions.assertThat(countBiannualAssessments).isNotEqualTo(0);
    }

    @Test
    public void should_return_assessment_history_for_CM() {

        List<BiAnnualAssessmentDto> historyBiannualAssessmentsByManagerId = careerService.findHistoryBiannualAssessmentsByManagerId(1L);
        Assertions.assertThat(historyBiannualAssessmentsByManagerId).size().isEqualTo(0);
    }

    @Test
    public  void should_delete_career_by_careerId() {
        Optional<CareerDto> careerBefore = careerService.findCareerById(3L);
        biannualAssessmentRepository.findByIdCareer(3L);
        log.info("career info before : " + careerBefore.get().toString());
        Assertions.assertThat(careerBefore.isEmpty()).isEqualTo(false);
        careerService.deleteCareerById(3L);
        Optional<CareerDto> careerAfter = careerService.findCareerById(3L);
        Assertions.assertThat(careerAfter.isEmpty()).isEqualTo(true);

    }


    private AssessmentCampaignRequestDto getCampaign() {
        return AssessmentCampaignRequestDto.builder()
                .semester(1)
                .year(2022)
                .companyId(1L)
                .build();
    }


    private BiannualAssessmentRequestDto getBiannualAssessment() {
        return BiannualAssessmentRequestDto.builder()
                .year(2020)
                .semester(2)
                .career(2L)
                .companyId(1L)
                // .creationDate(new Date())
                .corporate("B")
                .technicalSkill("A")
                .comment("")

                .customerRelationship("A")
                .functionalIndustryCompetency("A")
                .lastUpdate(new Date())
                .managementAbility("A")
                .internalTrainerPotential(false)
                .status(BiAnnualAssessmentStatus.Finished)
                .score(20)
                .sfEvolutivity(false)
                .bmEvolutivity(false)
                .teamSpirit("B")
                //.userUpdate(1L)
                .organisationalSkill("B")
                .globalPerformanceRating("R")

                .build();
    }
}
