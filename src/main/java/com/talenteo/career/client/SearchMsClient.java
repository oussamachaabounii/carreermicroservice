package com.talenteo.career.client;

import com.talenteo.career.model.entity.Career;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient(name = "search-ms", url = "${search.ms.url}")
@ApiIgnore
public interface SearchMsClient {

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/index-career")
    void indexCareer(@RequestBody Career career);

}