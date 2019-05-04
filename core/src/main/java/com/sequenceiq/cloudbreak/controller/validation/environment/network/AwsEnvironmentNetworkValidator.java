package com.sequenceiq.cloudbreak.controller.validation.environment.network;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform.AWS;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentNetworkV4Request;
import com.sequenceiq.cloudbreak.controller.validation.ValidationResult;

@Component
public class AwsEnvironmentNetworkValidator implements EnvironmentNetworkValidator {

    @Override
    public void validate(EnvironmentNetworkV4Request networkV4Request, ValidationResult.ValidationResultBuilder resultBuilder) {
        if (networkV4Request != null) {
            if (networkV4Request.getAws() != null) {
                if (networkV4Request.getSubnetIds().isEmpty()) {
                    resultBuilder.error(missingParamErrorMessage("subnet identifiers(subnetIds)"));
                }
                if (StringUtils.isEmpty(networkV4Request.getAws().getVpcId())) {
                    resultBuilder.error(missingParamErrorMessage("VPC identifier(vpcId)'"));
                }
            } else if (networkV4Request.getSubnetCidrs().isEmpty()) {
                resultBuilder.error(missingParamErrorMessage("subnetCidrs"));
            } else {
                resultBuilder.error(missingParamsErrorMsg());
            }
        }
    }

    @Override
    public CloudPlatform getCloudPlatform() {
        return AWS;
    }
}
