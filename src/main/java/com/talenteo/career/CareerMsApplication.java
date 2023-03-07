package com.talenteo.career;

import com.talenteo.career.config.CareerMsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Import;

@Import(CareerMsConfiguration.class)
@SpringBootApplication
public class CareerMsApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(CareerMsApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);

    }
}
