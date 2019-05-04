package com.sequenceiq.cloudbreak.cloud;

import java.util.Set;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;
import com.sequenceiq.cloudbreak.cloud.model.CreatedCloudNetwork;
import com.sequenceiq.cloudbreak.cloud.model.Region;

/**
 * Network connectors.
 */
public interface NetworkConnector {

    CreatedCloudNetwork createNetworkWithSubnets(CloudCredential cloudCredential, Region region, Set<String> subnetCidrs);

    CreatedCloudNetwork deleteNetworkWithSubnets(CloudCredential cloudCredential);

    CloudPlatform getCloudPlatform();

}
