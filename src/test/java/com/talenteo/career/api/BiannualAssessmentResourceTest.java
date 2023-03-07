package com.talenteo.career.api;

import com.easyms.test.AbstractResourceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talenteo.career.dto.AssessmentCampaignDto;
import com.talenteo.career.dto.AssessmentCampaignRequestDto;
import com.talenteo.career.dto.BiAnnualAssessmentDto;
import com.talenteo.career.dto.BiannualAssessmentRequestDto;
import com.talenteo.career.client.TdMsClient;
import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.service.CareerService;
import com.talenteo.career.service.ValidationService;
import com.talenteo.td.dto.TechnicalDocumentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "jean.dupont@titi.com", authorities = {"ROLE_ADMIN_CLIENT5"})
public class BiannualAssessmentResourceTest extends AbstractResourceTest {


    private static final String API_COMPAIGNS_BASE = "/api/v1/campaign-assessments";
    private static final String API_BA_BASE = "/api/v1/biannual-assessments";
    private static final String API_BA_BY_HR = "/api/v1/biannual-assessments/by-hr";
    private static final String API_BA_BY_MANAGER = "/api/v1/biannual-assessments/by-manager";
    private static final String API_BA_COUNT = "/api/v1/biannual-assessments-count";
    private static final String API_BA_LATEST_CAMPAIGN = "/api/v1/biannual-assessments/latest-campaign";

    @MockBean
    private TdMsClient tdMsClient;

    @MockBean
    private ValidationService validationService;

    @MockBean
    private CareerService careerService;

    @DisplayName("Get latest biannual assessment")
    @Test
    //@WithMockUser(username = "jean.dupont@toto.com", authorities = {"PERM_GET_HR2"})
    public void should_return_latest_biannual_assessment() throws Exception {
        mockMvc.perform(get(API_BA_LATEST_CAMPAIGN)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }
    @DisplayName("Get biannual assessment by hr id")
    @Test
    public void should_return_biannual_assessments_by_hr_id() throws Exception {

        doReturn(Arrays.asList(BiAnnualAssessmentDto.builder().build())).
                when(careerService).findHistoryBiannualAssessmentsByResourceId(anyLong());

        mockMvc.perform(get(API_BA_BY_HR)
                .param("hr_id", "4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }

    @DisplayName("throw exception when biannual assessment not found by hr id")
    @Test
    public void should_return_notfound_biannual_assessment_by_unknown_hr() throws Exception {
        doReturn(ResponseEntity.ok(TechnicalDocumentDto.builder().build())).when(tdMsClient).getByHrId(anyLong());

        mockMvc.perform(get(API_BA_BY_HR)
                .param("hr_id", "23")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());
    }

    @DisplayName("Get biannual assessments by manager id")
    @Test
    public void should_return_biannual_assessment_by_manager() throws Exception {
        doReturn(Arrays.asList(BiAnnualAssessmentDto.builder().build())).
                when(careerService).findHistoryBiannualAssessmentsByManagerId(anyLong());

        mockMvc.perform(get(API_BA_BY_MANAGER)
                .param("cm_id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }

    @DisplayName("create new campaign")
    @Test
    public void should_return_ok_when_create_campaign() throws Exception {
        doReturn(AssessmentCampaignDto.builder().build()).when(careerService).saveAssessmentCampaign(any(AssessmentCampaignRequestDto.class));
        mockMvc.perform(post(API_COMPAIGNS_BASE)
                .content(new ObjectMapper().writeValueAsString(AssessmentCampaignRequestDto.builder().build()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @DisplayName("create new biannual assessment")
    @Test
    public void should_return_ok_when_create_biannual_assessement() throws Exception {
        doReturn(BiAnnualAssessmentDto.builder().build()).when(careerService).saveBiannualAssessment(any(BiannualAssessmentRequestDto.class));
        mockMvc.perform(post(API_BA_BASE)
                .content(new ObjectMapper().writeValueAsString(BiannualAssessmentRequestDto.builder().build()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @DisplayName("Throws exception when validation fails in creation biannual assessment")
    @Test
    @WithMockUser(username = "jean.dupont@toto.com", authorities = {"PERM_FULL_CREATE_BA"})
    public void should_throw_exception_when_validation_create_biannual_assessement() throws Exception {
        doReturn(BiAnnualAssessmentDto.builder().build()).when(careerService).saveBiannualAssessment(any(BiannualAssessmentRequestDto.class));
        doThrow(new IllegalStateException()).when(validationService).validate(BiannualAssessmentRequestDto.builder().build());
        mockMvc.perform(post(API_BA_BASE)
                .content(new ObjectMapper().writeValueAsString(BiannualAssessmentRequestDto.builder().build()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
