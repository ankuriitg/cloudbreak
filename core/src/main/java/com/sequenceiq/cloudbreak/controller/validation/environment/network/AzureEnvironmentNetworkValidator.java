package com.sequenceiq.cloudbreak.controller.validation.environment.network;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform.AZURE;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.base.EnvironmentNetworkAzureV4Params;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentNetworkV4Request;
import com.sequenceiq.cloudbreak.controller.validation.ValidationResult;

@Component
public class AzureEnvironmentNetworkValidator implements EnvironmentNetworkValidator {

    @Override
    public void validate(EnvironmentNetworkV4Request networkV4Request, ValidationResult.ValidationResultBuilder resultBuilder) {
        EnvironmentNetworkAzureV4Params azureV4Params = networkV4Request.getAzure();
        if (azureV4Params != null) {
            if (networkV4Request.getSubnetIds().isEmpty()) {
                resultBuilder.error(missingParamErrorMessage("subnet identifiers(subnetIds)"));
            }
            if (StringUtils.isEmpty(azureV4Params.getNetworkId())) {
                resultBuilder.error(missingParamErrorMessage("network identifier(networkId)"));
            }
            if (StringUtils.isEmpty(azureV4Params.getResourceGroupName())) {
                resultBuilder.error(missingParamErrorMessage("resource group's name(resourceGroupName)"));
            }
        } else if (networkV4Request.getSubnetCidrs().isEmpty()) {
            resultBuilder.error(missingParamErrorMessage("subnetCidrs"));
        } else {
            resultBuilder.error(missingParamsErrorMsg());
        }
    }

    @Override
    public CloudPlatform getCloudPlatform() {
        return AZURE;
    }
}
