package com.talenteo.career.client;

import com.talenteo.td.dto.TechnicalDocumentDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api")
@FeignClient(name = "td-ms", url = "${td.ms.url}")
public interface TdMsClient {

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/technical-documents/{id}")
    ResponseEntity<TechnicalDocumentDto> getById(@PathVariable Long id) ;

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/technical-documents/by-hr")
    ResponseEntity<TechnicalDocumentDto> getByHrId(@RequestParam(name = "hr_id") Long id) ;


}
