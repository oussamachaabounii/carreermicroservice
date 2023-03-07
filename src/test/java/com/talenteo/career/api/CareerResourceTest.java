package com.talenteo.career.api;

import com.easyms.test.AbstractResourceTest;
import com.talenteo.career.dto.CareerDto;
import com.talenteo.career.client.TdMsClient;
import com.talenteo.career.repository.CareerRepository;
import com.talenteo.career.service.CareerService;
import com.talenteo.career.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CareerResourceTest extends AbstractResourceTest {
    private static final String BASE_URL_CAREER = "/api/v1/careers";

    @MockBean
    private TdMsClient tdMsClient;

    @MockBean
    private ValidationService validationService;

    @MockBean
    private CareerService careerService;

    @MockBean
    private CareerRepository careerRepository;


    @Test
    void should_return_career_by_id() throws Exception {
        doReturn(Optional.of(CareerDto.builder().build())).
                when(careerService).findCareerById(anyLong());
        mockMvc.perform(get(BASE_URL_CAREER + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());

    }

    @Test
    void should_return_career_by_hr_id() throws Exception {
        doReturn(Optional.of(CareerDto.builder().build())).
                when(careerService).findCareerByResourceId(anyLong());
        mockMvc.perform(get(BASE_URL_CAREER)
                .param("resourceId", "4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());

    }


}