package com.sequenceiq.environment.flow;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.cloud.event.Payload;
import com.sequenceiq.cloudbreak.cloud.event.Selectable;
import com.sequenceiq.cloudbreak.core.flow2.FlowLogService;
import com.sequenceiq.cloudbreak.core.flow2.FlowState;
import com.sequenceiq.cloudbreak.domain.FlowChainLog;
import com.sequenceiq.cloudbreak.domain.FlowLog;
import com.sequenceiq.cloudbreak.service.TransactionService;

@Service
public class FlowLogDbService implements FlowLogService {
    @Override
    public FlowLog save(String flowId, String flowChanId, String key, Payload payload, Map<Object, Object> variables, Class<?> flowType, FlowState currentState) {
        return null;
    }

    @Override
    public Iterable<FlowLog> saveAll(Iterable<FlowLog> entities) {
        return null;
    }

    @Override
    public FlowLog close(Long stackId, String flowId) throws TransactionService.TransactionExecutionException {
        return null;
    }

    @Override
    public FlowLog cancel(Long stackId, String flowId) throws TransactionService.TransactionExecutionException {
        return null;
    }

    @Override
    public FlowLog terminate(Long stackId, String flowId) throws TransactionService.TransactionExecutionException {
        return null;
    }

    @Override
    public void purgeTerminatedStacksFlowLogs() throws TransactionService.TransactionExecutionException {

    }

    @Override
    public void saveChain(String flowChainId, String parentFlowChainId, Queue<Selectable> chain) {

    }

    @Override
    public void updateLastFlowLogStatus(FlowLog lastFlowLog, boolean failureEvent) {

    }

    @Override
    public boolean isOtherFlowRunning(Long stackId) {
        return false;
    }

    @Override
    public boolean repeatedFlowState(FlowLog lastFlowLog, String event) {
        return false;
    }

    @Override
    public void updateLastFlowLogPayload(FlowLog lastFlowLog, Payload payload, Map<Object, Object> variables) {

    }

    @Override
    public Optional<FlowLog> getLastFlowLog(String flowId) {
        return Optional.empty();
    }

    @Override
    public Set<String> findAllRunningNonTerminationFlowIdsByStackId(Long stackId) {
        return null;
    }

    @Override
    public Optional<FlowLog> findFirstByFlowIdOrderByCreatedDesc(String flowId) {
        return Optional.empty();
    }

    @Override
    public Optional<FlowChainLog> findFirstByFlowChainIdOrderByCreatedDesc(String flowChainId) {
        return Optional.empty();
    }

    @Override
    public List<Object[]> findAllPending() {
        return null;
    }

    @Override
    public Set<FlowLog> findAllUnassigned() {
        return null;
    }

    @Override
    public Set<FlowLog> findAllByCloudbreakNodeId(String cloudbreakNodeId) {
        return null;
    }

    @Override
    public List<FlowLog> findAllByStackIdOrderByCreatedDesc(Long id) {
        return null;
    }

    @Override
    public Set<Long> findTerminatingStacksByCloudbreakNodeId(String id) {
        return null;
    }
}
