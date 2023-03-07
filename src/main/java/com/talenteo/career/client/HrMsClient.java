package com.talenteo.career.client;

import com.talenteo.hr.dto.HumanResourceDto;

import com.talenteo.hr.dto.HumanResourceLightDto;
import com.talenteo.hr.dto.SalaryHistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api")
@FeignClient(name = "hr-ms", url = "${hr.ms.url}")
public interface HrMsClient {

    @ApiIgnore
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/human-resources/{id}")
    ResponseEntity<HumanResourceDto> getById(@PathVariable Long id) ;

    @ApiIgnore
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/salaries/latest")
    public ResponseEntity<SalaryHistoryDto> getLatestSalaryByResource(@RequestParam Long resourceId) ;

}