package com.sequenceiq.environment.env.flow.envcreation;

import com.sequenceiq.cloudbreak.core.flow2.FlowState;

public enum EnvCreationState implements FlowState {
    INIT_STATE,
    ENV_CREATION_STARTED_STATE,
    ENV_CREATION_FINISHED_STATE,
    ENV_CREATION_FAILED_STATE,
    FINAL_STATE
}
