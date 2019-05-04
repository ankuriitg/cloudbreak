package com.sequenceiq.cloudbreak.cloud.handler;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.event.platform.CreateCloudNetworkRequest;
import com.sequenceiq.cloudbreak.cloud.event.platform.CreateCloudNetworkResult;
import com.sequenceiq.cloudbreak.cloud.init.CloudPlatformConnectors;
import com.sequenceiq.cloudbreak.cloud.model.CloudPlatformVariant;
import com.sequenceiq.cloudbreak.cloud.model.CreatedCloudNetwork;
import com.sequenceiq.cloudbreak.cloud.model.Platform;
import com.sequenceiq.cloudbreak.cloud.model.Region;
import com.sequenceiq.cloudbreak.cloud.model.Variant;

import reactor.bus.Event;

@Component
public class CreateCloudNetworksHandler implements CloudPlatformEventHandler<CreateCloudNetworkRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCloudNetworksHandler.class);

    @Inject
    private CloudPlatformConnectors cloudPlatformConnectors;

    @Override
    public Class<CreateCloudNetworkRequest> type() {
        return CreateCloudNetworkRequest.class;
    }

    @Override
    public void accept(Event<CreateCloudNetworkRequest> createCloudNetworkRequest) {
        LOGGER.debug("Received event: {}", createCloudNetworkRequest);
        CreateCloudNetworkRequest request = createCloudNetworkRequest.getData();

        try {
            CloudPlatformVariant cloudPlatformVariant = new CloudPlatformVariant(
                    Platform.platform(request.getExtendedCloudCredential().getCloudPlatform()),
                    Variant.variant(request.getVariant()));
            CreatedCloudNetwork createdCloudNetwork = cloudPlatformConnectors.get(cloudPlatformVariant)
                    .networkConnector()
                    .createNetworkWithSubnets(
                            request.getCloudCredential(), Region.region(request.getRegion()), request.getSubnetCidrs());
            CreateCloudNetworkResult createCloudNetworkResult = new CreateCloudNetworkResult(request, createdCloudNetwork);
            request.getResult().onNext(createCloudNetworkResult);
            LOGGER.debug("Create network finished.");
        } catch (Exception e) {
            request.getResult().onNext(new CreateCloudNetworkResult(e.getMessage(), e, request));
        }
    }
}
