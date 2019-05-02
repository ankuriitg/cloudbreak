package com.sequenceiq.environment.env.flow.envcreation;

import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationEvent.ENV_CREATION_FAILURE_HANDLED_EVENT;
import static com.sequenceiq.environment.env.flow.envcreation.EnvCreationEvent.FINISH_ENV_CREATION_EVENT;

import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import com.sequenceiq.cloudbreak.cloud.event.Payload;
import com.sequenceiq.cloudbreak.core.flow2.AbstractAction;
import com.sequenceiq.cloudbreak.core.flow2.CommonContext;

@Configuration
public class EnvCreationActions {

    @Bean(name = "ENV_CREATION_STARTED_STATE")
    public Action<?, ?> startAction() {
        return new AbstractEnvCreationAction<>(Payload.class) {
            @Override
            protected void doExecute(CommonContext context, Payload payload, Map<Object, Object> variables) {
                sendEvent(context.getFlowId(), FINISH_ENV_CREATION_EVENT.event(), payload);
            }
        };
    }

    @Bean(name = "ENV_CREATION_FINISHED_STATE")
    public Action<?, ?> finishedAction() {
        return new AbstractEnvCreationAction<>(Payload.class) {
            @Override
            protected void doExecute(CommonContext context, Payload payload, Map<Object, Object> variables) {
                sendEvent(context.getFlowId(), FINISH_ENV_CREATION_EVENT.event(), payload);
            }
        };
    }

    @Bean(name = "ENV_CREATION_FAILED_STATE")
    public Action<?, ?> failedAction() {
        return new AbstractEnvCreationAction<>(Payload.class) {

            @Override
            protected void doExecute(CommonContext context, Payload payload, Map<Object, Object> variables) {
                sendEvent(context.getFlowId(), ENV_CREATION_FAILURE_HANDLED_EVENT.event(), payload);
            }
        };
    }

    private abstract static class AbstractEnvCreationAction<P extends Payload> extends AbstractAction<EnvCreationState, EnvCreationEvent, CommonContext, P> {

        protected AbstractEnvCreationAction(Class<P> payloadClass) {
            super(payloadClass);
        }

        @Override
        protected CommonContext createFlowContext(String flowId, StateContext<EnvCreationState, EnvCreationEvent> stateContext, P payload) {
            return new CommonContext(flowId);
        }

        @Override
        protected Object getFailurePayload(P payload, Optional<CommonContext> flowContext, Exception ex) {
            return (Payload) () -> null;
        }
    }
}
