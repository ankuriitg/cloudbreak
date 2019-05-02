package com.sequenceiq.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.web.servlet.WebMvcMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
//@EnableJpaRepositories(basePackages = { "com.sequenceiq.cloudbreak.repository", "com.sequenceiq.freeipa.repository" })
@SpringBootApplication(scanBasePackages = {"com.sequenceiq.environment", "com.sequenceiq.cloudbreak"},
        exclude = WebMvcMetricsAutoConfiguration.class)
public class EnvironmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnvironmentApplication.class, args);
    }

}