package com.talenteo.career.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.talenteo.career", "com.easyms.common","com.talenteo.common"})
@EnableJpaRepositories(basePackages = "com.talenteo.career.repository")
@EnableFeignClients(basePackages = "com.talenteo.career")
@EnableWebSecurity
public class CareerMsConfiguration {



}
