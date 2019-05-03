package com.sequenceiq.environment.flow;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.core.flow2.ApplicationFlowInformation;
import com.sequenceiq.cloudbreak.core.flow2.config.FlowConfiguration;
import com.sequenceiq.environment.env.flow.envcreation.EnvCreationFlowConfiguration;

@Component
public class EnvironmentFlowInformation implements ApplicationFlowInformation {

    @Override
    public List<Class<? extends FlowConfiguration<?>>> getRestartableFlows() {
        return List.of(EnvCreationFlowConfiguration.class);
    }

    @Override
    public List<String> getAllowedParallelFlows() {
        return Collections.emptyList();
    }
}
