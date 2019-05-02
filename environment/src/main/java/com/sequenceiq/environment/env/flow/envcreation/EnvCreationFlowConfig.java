package com.sequenceiq.environment.env.flow.envcreation;


import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationEvent.ENV_CREATION_FAILURE_HANDLED_EVENT;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationEvent.FINISH_ENV_CREATION_EVENT;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationEvent.START_ENV_CREATION_EVENT;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationEvent.FINALIZE_ENV_CREATION_EVENT;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationState.ENV_CREATION_FAILED_STATE;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationState.ENV_CREATION_FINISHED_STATE;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationState.ENV_CREATION_STARTED_STATE;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationState.FINAL_STATE;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationState.INIT_STATE;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.core.flow2.config.AbstractFlowConfiguration;
import com.sequenceiq.cloudbreak.core.flow2.config.AbstractFlowConfiguration.Transition.Builder;
import com.sequenceiq.cloudbreak.core.flow2.config.RetryableFlowConfiguration;

@Component
public class EnvCreationFlowConfig extends AbstractFlowConfiguration<EnvCreationState, EnvCreationEvent> implements RetryableFlowConfiguration<EnvCreationEvent> {
    private static final List<Transition<EnvCreationState, EnvCreationEvent>> TRANSITIONS = new Builder<EnvCreationState, EnvCreationEvent>()
            .defaultFailureEvent(EnvCreationEvent.ENV_CREATION_FAILED_EVENT)
            .from(INIT_STATE).to(ENV_CREATION_STARTED_STATE).event(EnvCreationEvent.START_ENV_CREATION_EVENT).noFailureEvent()
            .from(ENV_CREATION_STARTED_STATE).to(ENV_CREATION_FINISHED_STATE).event(FINISH_ENV_CREATION_EVENT).defaultFailureEvent()
            .from(ENV_CREATION_FINISHED_STATE).to(FINAL_STATE).event(FINALIZE_ENV_CREATION_EVENT).defaultFailureEvent()
            .build();

    private static final FlowEdgeConfig<EnvCreationState, EnvCreationEvent> EDGE_CONFIG =
            new FlowEdgeConfig<>(INIT_STATE, FINAL_STATE, ENV_CREATION_FAILED_STATE, ENV_CREATION_FAILURE_HANDLED_EVENT);

    public EnvCreationFlowConfig() {
        super(EnvCreationState.class, EnvCreationEvent.class);
    }

    @Override
    public EnvCreationEvent[] getEvents() {
        return EnvCreationEvent.values();
    }

    @Override
    public EnvCreationEvent[] getInitEvents() {
        return new EnvCreationEvent[]{
                START_ENV_CREATION_EVENT
        };
    }

    @Override
    public EnvCreationEvent getFailHandledEvent() {
        return ENV_CREATION_FAILURE_HANDLED_EVENT;
    }

    @Override
    protected List<Transition<EnvCreationState, EnvCreationEvent>> getTransitions() {
        return TRANSITIONS;
    }

    @Override
    protected FlowEdgeConfig<EnvCreationState, EnvCreationEvent> getEdgeConfig() {
        return EDGE_CONFIG;
    }
}
