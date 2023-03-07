package com.talenteo.career.service;

import com.talenteo.career.CareerMsApplication;
import com.talenteo.career.client.HrMsClient;
import com.talenteo.career.client.TdMsClient;
import com.talenteo.career.dto.BiannualAssessmentRequestDto;
import com.talenteo.career.model.entity.BiannualAssessment;
import com.talenteo.career.repository.BiannualAssessmentRepository;
import com.talenteo.career.repository.CareerRepository;
import com.talenteo.career.utils.CareerMessages;
import com.talenteo.common.error.TalenteoCommonMessages;
import com.talenteo.hr.dto.HumanResourceDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = CareerMsApplication.class)
class ValidationServiceTest {
    @Inject
    private ValidationService validationService;

    @MockBean
    BiannualAssessmentRepository biannualAssessmentRepository;

    @MockBean
    private HrMsClient hrMsClient;

    @Test
    public void should_throw_exception_if_resource_not_found() {

        doReturn(ResponseEntity.notFound().build()).when(hrMsClient).getById(anyLong());

        Throwable throwable = catchThrowable(() -> validationService.validateResource(1L));
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo(TalenteoCommonMessages.hr_not_found.getErrorKey());

    }

    @Test
    public void should_throw_career_not_valid_exception_if_career_id_is_null_whenValidation() {

        Throwable throwable = catchThrowable(() ->
                validationService.validate(BiannualAssessmentRequestDto.builder().career(null).build()));
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo(CareerMessages.id_not_valid.getErrorKey());

    }


    @Test
    public void should_throw_assessment_exists_exception_if_career_id_is_null_whenValidation() {

        doReturn(Optional.of(BiannualAssessment.builder().build())).
                when(biannualAssessmentRepository).findById(any());

        Throwable throwable = catchThrowable(() ->
                validationService.validate(BiannualAssessmentRequestDto.builder()
                        .career(1L)
                        .year(2020)
                        .semester(2)
                        .build()));

        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo(CareerMessages.biannual_assessment_already_exist.getErrorKey());

    }

    @Test
    public void should_validate_resource_if_it_exists() {
        doReturn(ResponseEntity.ok().build()).when(hrMsClient).getById(anyLong());

        Throwable throwable = catchThrowable(() -> validationService.validateResource(1L));
        assertThat(throwable).isNull();


    }


}