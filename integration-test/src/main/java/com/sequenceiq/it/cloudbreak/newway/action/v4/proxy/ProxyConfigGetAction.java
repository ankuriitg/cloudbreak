package com.sequenceiq.it.cloudbreak.newway.action.v4.proxy;

import static com.sequenceiq.it.cloudbreak.newway.log.Log.log;
import static com.sequenceiq.it.cloudbreak.newway.log.Log.logJSON;
import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.it.cloudbreak.newway.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.newway.action.Action;
import com.sequenceiq.it.cloudbreak.newway.context.TestContext;
import com.sequenceiq.it.cloudbreak.newway.entity.proxy.ProxyTestDto;

public class ProxyConfigGetAction implements Action<ProxyTestDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyConfigGetAction.class);

    @Override
    public ProxyTestDto action(TestContext testContext, ProxyTestDto entity, CloudbreakClient client) throws Exception {
        log(LOGGER, format(" Name: %s", entity.getRequest().getName()));
        logJSON(LOGGER, " Proxy config get request:\n", entity.getRequest());
        entity.setResponse(
                client.getCloudbreakClient()
                        .proxyConfigV4Endpoint()
                        .get(client.getWorkspaceId(), entity.getName()));
        logJSON(LOGGER, " Proxy config was get successfully:\n", entity.getResponse());
        log(LOGGER, format(" ID: %s", entity.getResponse().getId()));
        return entity;
    }

}