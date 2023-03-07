package com.talenteo.career.api;

import com.talenteo.career.dto.AssessmentCampaignDto;
import com.talenteo.career.dto.BiAnnualAssessmentDto;
import com.talenteo.career.dto.BiannualAssessmentRequestDto;
import com.talenteo.career.dto.BiannualAssessmentsCountDto;
import com.talenteo.career.service.CareerService;
import com.talenteo.career.service.ValidationService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
@Timed
public class BiannualAssessmentResource {

    private final CareerService careerService;
    private final ValidationService validationService;

    /**
     * =======================================================
     * Create new biannual assessment
     * =======================================================
     *
     * @param assessmentRequestDto
     * @return
     */
    @ApiOperation("Create new biannual assessment ")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA²')")
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/biannual-assessments")
    public ResponseEntity<BiAnnualAssessmentDto> create(@RequestBody @Valid BiannualAssessmentRequestDto assessmentRequestDto) {
        log.info("create new Biannual Assessment : {} ", assessmentRequestDto);
        validationService.validate(assessmentRequestDto);
        BiAnnualAssessmentDto dto = careerService.saveBiannualAssessment(assessmentRequestDto);
        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().
                path("/api/v1/biannualAssessments/{id}").build().expand(dto.getYear()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    /**
     * =======================================================
     * Create new biannual assessment
     * =======================================================
     *
     * @param assessmentRequestDto
     * @return
     */
    @ApiOperation("Update new biannual assessment ")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/v1/biannual-assessments")
    public ResponseEntity<BiAnnualAssessmentDto> update(@RequestParam("semester") Integer semester, @RequestParam("year") Integer year, @RequestParam("career_id") Long careerId, @RequestBody @Valid BiannualAssessmentRequestDto assessmentRequestDto) {
        log.info("update Biannual Assessment : {} ", assessmentRequestDto);
        BiAnnualAssessmentDto dto = careerService.updateBiannualAssessment(semester, year, careerId, assessmentRequestDto);
        return ResponseEntity.ok().body(dto);
    }


    /**
     * =======================================================
     * Get biannual by hr ID
     * =======================================================
     *
     * @param hrId
     * @return
     */
    @ApiOperation("Get biannual assessment by HR ID ")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/biannual-assessments/by-hr")

    public ResponseEntity<List<BiAnnualAssessmentDto>> findBiannualAssessmentByhrId(@RequestParam(name = "hr_id") Long hrId) {

        log.info("biannual assessment by HR ID {} ", hrId);
        List<BiAnnualAssessmentDto> biAnnualAssessmentDto = careerService.findHistoryBiannualAssessmentsByResourceId(hrId);
        if (biAnnualAssessmentDto.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(biAnnualAssessmentDto, HttpStatus.OK);
    }

    /**
     * =======================================================
     * Get the biannual assessments count by Manager ID
     * =======================================================
     *
     * @param cmId
     * @return
     */
    @ApiOperation("Get the biannual assessments count by Manager Id")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/biannual-assessments-count")
    public ResponseEntity<BiannualAssessmentsCountDto> findAnnualassessmentCountByCmId(@RequestParam(name = "cm_id") Long cmId) {
        log.info("biannual assessment count by CM ID {} ", cmId);
        BiannualAssessmentsCountDto biannualAssessmentsCountDto = careerService.countBiannualAssessments(cmId);
        return ResponseEntity.ok().body(biannualAssessmentsCountDto);
    }

    @ApiOperation("Get the biannual assessments count by Manager Id")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/biannual-assessments-nb")
    public ResponseEntity<Map<String, Long>> findAssessmentCount(@RequestParam(name = "cm_id") Long cmId) {
        log.info("biannual assessment count by CM ID {} ", cmId);
        Map<String, Long> biannualAssessmentsCountDto = careerService.statsBiannualAssessments(cmId);
        return ResponseEntity.ok().body(biannualAssessmentsCountDto);
    }


    /**
     * =======================================================
     * Get the biannual assessments history by Manager ID
     * =======================================================
     *
     * @param cmId
     * @return
     */
    @ApiOperation("Get the biannual assessments history by Manager Id")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/biannual-assessments/by-manager")
    public ResponseEntity<BiannualAssessmentsCountDto> findBiannualAssessmentsByCmId(@RequestParam(name = "cm_id") Long cmId) {
        log.info("biannual assessments by CM ID {} ", cmId);
        List<BiAnnualAssessmentDto> biAnnualAssessmentDtoList = careerService.findHistoryBiannualAssessmentsByManagerId(cmId);
        if (biAnnualAssessmentDtoList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity(biAnnualAssessmentDtoList, HttpStatus.OK);
    }

    /**
     * =======================================================
     * Get latest assessment campaign
     * =======================================================
     *
     * @return
     */
    @ApiOperation("Get latest assessment campaign")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/biannual-assessments/latest-campaign")
    public ResponseEntity<AssessmentCampaignDto> getBiannualAssessmentPeriod() {
        log.info("Get biannual latest campaign ");
        AssessmentCampaignDto assessmentCampaignDto = careerService.findLatestAssessmentCampaign();
        return ResponseEntity.ok().body(assessmentCampaignDto);
    }


}