package com.talenteo.career.api;

import com.talenteo.career.dto.*;
import com.talenteo.career.model.entity.AssessmentCampaign;
import com.talenteo.career.service.CareerService;
import com.talenteo.td.dto.DegreeDto;
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
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
@Timed
public class CareerResource {

    private final CareerService careerService;

    /**
     * =======================================================
     * get career by id
     * =======================================================
     *
     * @param id
     * @return
     */
    @ApiOperation("get career by id")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_CAREER')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/careers/{id}")
    public ResponseEntity<CareerDto> getCareer(@PathVariable Long id) {
        log.info("find career by id {} ", id);
        Optional<CareerDto> career = careerService.findCareerById(id);
        return career.map(c -> ResponseEntity.ok().body(c)).orElse(ResponseEntity.notFound().build());

    }

    /**
     * =======================================================
     * get career by HR ID
     * =======================================================
     *
     * @param resourceId
     * @return
     */
    @ApiOperation("get career by HR ID")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_CAREER')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/careers")
    public ResponseEntity<CareerDto> getCareerByResourceId(@RequestParam Long resourceId) {
        log.info("find career by resourceId {} ", resourceId);
        Optional<CareerDto> career = careerService.findCareerByResourceId(resourceId);
        return career.map(c -> ResponseEntity.ok().body(c)).orElse(ResponseEntity.notFound().build());

    }


    /**
     * =======================================================
     * get career list by Manager
     * =======================================================
     *
     * @param managerId
     * @return
     */
    @ApiOperation("get careers by manager ID")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_CAREER')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/careers/by-manager")
    public ResponseEntity<List<CareerDto>> getCareersByManagerId(@RequestParam Long managerId) {
        log.info("find careers by managerId {} ", managerId);
        List<CareerDto> careerDtoList = careerService.findCareersByManagerId(managerId);
        return ResponseEntity.ok(careerDtoList);

    }

    /**
     * =======================================================
     * Create new career
     * =======================================================
     *
     * @param careerRequestDto
     * @return
     */
    @ApiOperation("Create new biannual assessment ")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA²')")
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/careers")
    public ResponseEntity<CareerDto> create(@RequestBody @Valid CareerRequestDto careerRequestDto) {
        log.info("create new career : {} ", careerRequestDto);
        CareerDto careerDto = careerService.saveCareer(careerRequestDto);
        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().
                path("/api/v1/careers/{id}").build().expand(careerDto.getId()).toUri();
        return ResponseEntity.created(location).body(careerDto);
    }


    @ApiOperation("Create new campaign assessment ")
    @Timed
    //@PreAuthorize("hasAnyAuthority('PERM_GET_BA²')")
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/campaign-assessments")
    public ResponseEntity<AssessmentCampaignDto> create(@RequestBody @Valid AssessmentCampaignRequestDto assessmentCampaignRequestDto) {
        log.info("create new assessmentCampaign : {} ", assessmentCampaignRequestDto);
        AssessmentCampaignDto assessmentCampaignDto = careerService.saveAssessmentCampaign(assessmentCampaignRequestDto);
        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().
                path("/api/v1/campaign-assessments/{id}").build().expand(assessmentCampaignDto.getYear()).toUri();
        return ResponseEntity.created(location).body(assessmentCampaignDto);
    }

    @ApiOperation("search all assessment campaigns by company")
    @Timed
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/v1/campaign-assessments")
    ResponseEntity<List<AssessmentCampaignDto>> getAllAssessmentCampaigns(@RequestParam(name = "company_id") Long companyId) {
        log.info("search all assessment campaigns by company = {}", companyId);
        List<AssessmentCampaignDto> assessmentCampaigns = careerService.getAllByCompanyId(companyId);
        return ResponseEntity.ok().body(assessmentCampaigns);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCareerById(@PathVariable("id") long careerId) {
        log.info("Deleting career with id: {}", careerId);
        Optional<CareerDto> careerToDelete = careerService.findCareerById(careerId);

        if (careerToDelete.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        careerService.deleteCareerById(careerId);

        return new ResponseEntity<Void>(HttpStatus.OK);

    }
}
