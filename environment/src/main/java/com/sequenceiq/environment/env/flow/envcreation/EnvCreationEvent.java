package com.sequenceiq.environment.env.flow.envcreation;

import com.sequenceiq.cloudbreak.core.flow2.FlowEvent;

public enum EnvCreationEvent implements FlowEvent {
    START_ENV_CREATION_EVENT,
    FINISH_ENV_CREATION_EVENT,
    FINALIZE_ENV_CREATION_EVENT,

    ENV_CREATION_FAILED_EVENT,
    ENV_CREATION_FAILURE_HANDLED_EVENT;

    @Override
    public String event() {
        return name();
    }
}
