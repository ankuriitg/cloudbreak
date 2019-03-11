package com.sequenceiq.it.cloudbreak.newway.action.v4.environment;

import static com.sequenceiq.it.cloudbreak.newway.log.Log.logJSON;

import com.sequenceiq.it.cloudbreak.newway.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.newway.action.Action;
import com.sequenceiq.it.cloudbreak.newway.context.TestContext;
import com.sequenceiq.it.cloudbreak.newway.entity.environment.EnvironmentTestDto;

public class EnvironmentCreateAction implements Action<EnvironmentTestDto> {

    @Override
    public EnvironmentTestDto action(TestContext testContext, EnvironmentTestDto entity, CloudbreakClient cloudbreakClient) throws Exception {
        entity.setResponse(
                cloudbreakClient.getCloudbreakClient()
                        .environmentV4Endpoint()
                        .post(cloudbreakClient.getWorkspaceId(), entity.getRequest()));

        logJSON("Environment post request: ", entity.getRequest());
        return entity;
    }
}