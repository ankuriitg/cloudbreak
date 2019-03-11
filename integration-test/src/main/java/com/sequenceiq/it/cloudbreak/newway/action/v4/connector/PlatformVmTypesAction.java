package com.sequenceiq.it.cloudbreak.newway.action.v4.connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.it.cloudbreak.newway.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.newway.action.Action;
import com.sequenceiq.it.cloudbreak.newway.context.TestContext;
import com.sequenceiq.it.cloudbreak.newway.entity.connector.PlatformVmTypesTestDto;

public class PlatformVmTypesAction implements Action<PlatformVmTypesTestDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformVmTypesAction.class);

    @Override
    public PlatformVmTypesTestDto action(TestContext testContext, PlatformVmTypesTestDto entity, CloudbreakClient cloudbreakClient) throws Exception {
        String logInitMessage = "Obtaining vm types by credential";
        LOGGER.info("{}", logInitMessage);
        entity.setResponse(
                cloudbreakClient.getCloudbreakClient().connectorV4Endpoint().getVmTypesByCredential(
                        cloudbreakClient.getWorkspaceId(), entity.getCredentialName(), entity.getRegion(), entity.getPlatformVariant(), entity.getAvailabilityZone())
        );
        LOGGER.info("{} was successful", logInitMessage);
        return entity;
    }
}
