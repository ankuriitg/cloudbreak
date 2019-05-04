package com.sequenceiq.cloudbreak.cloud.event.platform;

import java.util.Set;

import com.sequenceiq.cloudbreak.cloud.event.CloudPlatformRequest;
import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;
import com.sequenceiq.cloudbreak.cloud.model.ExtendedCloudCredential;

public class CreateCloudNetworkRequest extends CloudPlatformRequest<CreateCloudNetworkResult> {

    private final ExtendedCloudCredential extendedCloudCredential;

    private final String variant;

    private final String region;

    private final Set<String> subnetCidrs;

    public CreateCloudNetworkRequest(
            CloudCredential cloudCredential,
            ExtendedCloudCredential extendedCloudCredential,
            String variant,
            String region,
            Set<String> subnetCidrs) {
        super(null, cloudCredential);
        this.extendedCloudCredential = extendedCloudCredential;
        this.subnetCidrs = subnetCidrs;
        this.region = region;
        this.variant = variant;
    }

    public String getRegion() {
        return region;
    }

    public Set<String> getSubnetCidrs() {
        return subnetCidrs;
    }

    public ExtendedCloudCredential getExtendedCloudCredential() {
        return extendedCloudCredential;
    }

    public String getVariant() {
        return variant;
    }
}
