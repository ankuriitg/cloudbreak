package com.sequenceiq.cloudbreak.cloud.aws;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform.AWS;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateSubnetRequest;
import com.amazonaws.services.ec2.model.CreateSubnetResult;
import com.amazonaws.services.ec2.model.CreateVpcRequest;
import com.amazonaws.services.ec2.model.CreateVpcResult;
import com.amazonaws.services.ec2.model.Subnet;
import com.amazonaws.services.ec2.model.Vpc;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.cloud.NetworkConnector;
import com.sequenceiq.cloudbreak.cloud.aws.view.AwsCredentialView;
import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;
import com.sequenceiq.cloudbreak.cloud.model.CreatedCloudNetwork;
import com.sequenceiq.cloudbreak.cloud.model.Region;

@Service
public class AwsNetworkConnector implements NetworkConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsNetworkConnector.class);
    @Inject
    private AwsClient awsClient;

    @Override
    public CreatedCloudNetwork createNetworkWithSubnets(CloudCredential cloudCredential, Region region, Set<String> subnetCidrs) {
        AmazonEC2Client ec2Client = awsClient.createAccess(new AwsCredentialView(cloudCredential), region.value());

        CreateVpcRequest newVPC = new CreateVpcRequest("");
        CreateVpcResult res = ec2Client.createVpc(newVPC);
        Vpc vpc = res.getVpc();
        String vpcId = vpc.getVpcId();

        Set<String> subnetIds = new HashSet<>();
        for (String subnetCidr : subnetCidrs) {
            CreateSubnetRequest createSubnetRequest = new CreateSubnetRequest(vpcId, subnetCidr);
            CreateSubnetResult createSubnetResult = ec2Client.createSubnet(createSubnetRequest);
            Subnet subnet = createSubnetResult.getSubnet();
            String subnetId = subnet.getSubnetId();
            subnetIds.add(subnetId);
        }

        return new CreatedCloudNetwork(vpcId, subnetIds);
    }

    @Override
    public CreatedCloudNetwork deleteNetworkWithSubnets(CloudCredential cloudCredential) {
        return null;
    }

    @Override
    public CloudPlatform getCloudPlatform() {
        return AWS;
    }
}
