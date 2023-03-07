package com.talenteo.career.utils;


import com.easyms.rest.ms.error.CommonErrorMessages;
import com.easyms.rest.ms.error.ErrorMessage;
import com.easyms.rest.ms.error.ErrorRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CareerMessages extends CommonErrorMessages {

    public static final String RESOURCE_NOT_FOUND = "resource.not.found";
    public static final String BIANNUAL_ASSESSMENT_ALREADY_EXIST = "biannual.assessment.already.exist";
    public static final String ID_NOT_VALID = "id.not.valid";

    /****************************************/

    public static ErrorMessage resource_not_found;
    public static ErrorMessage id_not_valid;
    public static ErrorMessage biannual_assessment_already_exist;

    @PostConstruct

    public void load() {
        id_not_valid = ErrorRepository.build(ID_NOT_VALID, "id not valid");
        biannual_assessment_already_exist = ErrorRepository.build(BIANNUAL_ASSESSMENT_ALREADY_EXIST, "biannual assessment already exist");
    }

}

