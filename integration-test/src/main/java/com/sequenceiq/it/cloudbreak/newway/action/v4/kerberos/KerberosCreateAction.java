package com.sequenceiq.it.cloudbreak.newway.action.v4.kerberos;

import static com.sequenceiq.it.cloudbreak.newway.log.Log.log;
import static com.sequenceiq.it.cloudbreak.newway.log.Log.logJSON;
import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.it.cloudbreak.newway.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.newway.action.Action;
import com.sequenceiq.it.cloudbreak.newway.context.TestContext;
import com.sequenceiq.it.cloudbreak.newway.dto.kerberos.KerberosTestDto;

public class KerberosCreateAction implements Action<KerberosTestDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KerberosCreateAction.class);

    public KerberosTestDto action(TestContext testContext, KerberosTestDto testDto, CloudbreakClient client) throws Exception {
        log(LOGGER, format(" Name: %s", testDto.getRequest().getName()));
        logJSON(LOGGER, format(" Kerberos post request:%n"), testDto.getRequest());
        testDto.setResponse(
                client.getCloudbreakClient()
                        .kerberosConfigV4Endpoint()
                        .create(client.getWorkspaceId(), testDto.getRequest()));
        logJSON(LOGGER, format(" Kerberos created  successfully:%n"), testDto.getResponse());
        log(LOGGER, format(" ID: %s", testDto.getResponse().getId()));
        return testDto;
    }
}
