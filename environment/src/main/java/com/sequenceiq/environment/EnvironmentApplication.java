package com.sequenceiq.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.web.servlet.WebMvcMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.sequenceiq.cloudbreak.concurrent.MDCCleanerTaskDecorator;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableJpaRepositories(basePackages = {"com.sequenceiq.cloudbreak"})
@SpringBootApplication(scanBasePackages = {"com.sequenceiq.environment", "com.sequenceiq.cloudbreak"},
        exclude = WebMvcMetricsAutoConfiguration.class)
public class EnvironmentApplication {

    @Value("${cb.intermediate.threadpool.core.size:}")
    private int intermediateCorePoolSize;

    @Value("${cb.intermediate.threadpool.capacity.size:}")
    private int intermediateQueueCapacity;

    public static void main(String[] args) {
        SpringApplication.run(EnvironmentApplication.class, args);
    }

    // these are needed only temporarily:

    @Bean
    @Primary
    public ThreadPoolTaskScheduler vaultThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("vault-ThreadPoolTaskScheduler-");
        threadPoolTaskScheduler.setDaemon(true);
        return threadPoolTaskScheduler;
    }

    @Bean
    public AsyncTaskExecutor intermediateBuilderExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(intermediateCorePoolSize);
        executor.setQueueCapacity(intermediateQueueCapacity);
        executor.setThreadNamePrefix("intermediateBuilderExecutor-");
        executor.setTaskDecorator(new MDCCleanerTaskDecorator());
        executor.initialize();
        return executor;
    }
}