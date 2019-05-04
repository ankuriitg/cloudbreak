package com.sequenceiq.cloudbreak.service.stack;

import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.cloud.event.model.EventStatus;
import com.sequenceiq.cloudbreak.cloud.event.platform.CreateCloudNetworkRequest;
import com.sequenceiq.cloudbreak.cloud.event.platform.CreateCloudNetworkResult;
import com.sequenceiq.cloudbreak.cloud.model.CreatedCloudNetwork;
import com.sequenceiq.cloudbreak.cloud.model.ExtendedCloudCredential;
import com.sequenceiq.cloudbreak.cloud.reactor.ErrorHandlerAwareReactorEventFactory;
import com.sequenceiq.cloudbreak.converter.spi.CredentialToExtendedCloudCredentialConverter;
import com.sequenceiq.cloudbreak.domain.Credential;
import com.sequenceiq.cloudbreak.service.OperationException;

import reactor.bus.EventBus;

@Service
public class CreateCloudResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudParameterService.class);

    @Inject
    private EventBus eventBus;

    @Inject
    private ErrorHandlerAwareReactorEventFactory eventFactory;

    @Inject
    private CredentialToExtendedCloudCredentialConverter credentialToExtendedCloudCredentialConverter;

    public CreatedCloudNetwork createdCloudNetwork(Credential credential, String cloudPlatform, Set<String> subnetCidrs) {
        LOGGER.debug("Create networks");
        ExtendedCloudCredential cloudCredential = credentialToExtendedCloudCredentialConverter.convert(credential);
        CreateCloudNetworkRequest createCloudNetworkRequest = new CreateCloudNetworkRequest(
                cloudCredential,
                cloudCredential,
                cloudPlatform,
                cloudPlatform,
                subnetCidrs
        );
        eventBus.notify(createCloudNetworkRequest.selector(), eventFactory.createEvent(createCloudNetworkRequest));
        try {
            CreateCloudNetworkResult res = createCloudNetworkRequest.await();
            LOGGER.debug("Create network result: {}", res);
            if (res.getStatus().equals(EventStatus.FAILED)) {
                LOGGER.debug("Failed to create networks", res.getErrorDetails());
                throw new GetCloudParameterException(res.getErrorDetails());
            }
            return res.getCreatedCloudNetwork();
        } catch (InterruptedException e) {
            LOGGER.error("Error while creating networks", e);
            throw new OperationException(e);
        }
    }
}
