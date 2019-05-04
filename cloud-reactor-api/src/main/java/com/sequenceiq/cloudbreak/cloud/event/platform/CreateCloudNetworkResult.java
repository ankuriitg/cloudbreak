package com.sequenceiq.cloudbreak.cloud.event.platform;

import com.sequenceiq.cloudbreak.cloud.event.CloudPlatformRequest;
import com.sequenceiq.cloudbreak.cloud.event.CloudPlatformResult;
import com.sequenceiq.cloudbreak.cloud.model.CreatedCloudNetwork;

public class CreateCloudNetworkResult extends CloudPlatformResult<CloudPlatformRequest<?>> {
    private CreatedCloudNetwork createdCloudNetwork;

    public CreateCloudNetworkResult(CreatedCloudNetwork createdCloudNetwork) {
        this.createdCloudNetwork = createdCloudNetwork;
    }

    public CreateCloudNetworkResult(CloudPlatformRequest<?> request, CreatedCloudNetwork createdCloudNetwork) {
        super(request);
        this.createdCloudNetwork = createdCloudNetwork;
    }

    public CreateCloudNetworkResult(String statusReason, Exception errorDetails, CloudPlatformRequest<?> request) {
        super(statusReason, errorDetails, request);
    }

    public CreatedCloudNetwork getCreatedCloudNetwork() {
        return createdCloudNetwork;
    }
}
