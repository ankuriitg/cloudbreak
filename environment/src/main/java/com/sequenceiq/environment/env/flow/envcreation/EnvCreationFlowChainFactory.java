package com.sequenceiq.environment.env.flow.envcreation;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.event.Selectable;
import com.sequenceiq.cloudbreak.core.flow2.chain.FlowEventChainFactory;
import com.sequenceiq.cloudbreak.reactor.api.event.BaseFlowEvent;

@Component
public class EnvCreationFlowChainFactory implements FlowEventChainFactory<BaseFlowEvent> {
    @Override
    public String initEvent() {
        return "ENV_CREATION_CHAIN_EVENT";
    }

    @Override
    public Queue<Selectable> createFlowTriggerEventQueue(BaseFlowEvent event) {
        Queue<Selectable> flowChainTriggers = new ConcurrentLinkedDeque<>();
        flowChainTriggers.add(new BaseFlowEvent(EnvCreationEvent.START_ENV_CREATION_EVENT.event(), event.getStackId(), event.accepted()));
        return flowChainTriggers;
    }
}
