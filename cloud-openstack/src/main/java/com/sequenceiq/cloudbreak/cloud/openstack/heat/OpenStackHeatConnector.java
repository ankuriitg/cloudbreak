package com.sequenceiq.cloudbreak.cloud.openstack.heat;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.cloud.Authenticator;
import com.sequenceiq.cloudbreak.cloud.CloudConnector;
import com.sequenceiq.cloudbreak.cloud.CredentialConnector;
import com.sequenceiq.cloudbreak.cloud.InstanceConnector;
import com.sequenceiq.cloudbreak.cloud.MetadataCollector;
import com.sequenceiq.cloudbreak.cloud.PlatformParameters;
import com.sequenceiq.cloudbreak.cloud.ResourceConnector;
import com.sequenceiq.cloudbreak.cloud.Setup;
import com.sequenceiq.cloudbreak.cloud.Validator;
import com.sequenceiq.cloudbreak.cloud.model.Platform;
import com.sequenceiq.cloudbreak.cloud.model.Variant;
import com.sequenceiq.cloudbreak.cloud.openstack.auth.OpenStackAuthenticator;
import com.sequenceiq.cloudbreak.cloud.openstack.auth.OpenStackCredentialConnector;
import com.sequenceiq.cloudbreak.cloud.openstack.common.OpenStackConstants;
import com.sequenceiq.cloudbreak.cloud.openstack.common.OpenStackParameters;
import com.sequenceiq.cloudbreak.cloud.openstack.common.OpenStackSetup;
import com.sequenceiq.cloudbreak.cloud.openstack.common.OpenStackTagValidator;

@Service
public class OpenStackHeatConnector implements CloudConnector {

    @Inject
    private OpenStackCredentialConnector credentialConnector;

    @Inject
    private OpenStackAuthenticator authenticator;

    @Inject
    private OpenStackResourceConnector resourceConnector;

    @Inject
    private OpenStackInstanceConnector instanceConnector;

    @Inject
    private OpenStackMetadataCollector metadataCollector;

    @Inject
    private OpenStackSetup openStackSetup;

    @Inject
    private OpenStackTagValidator openStackTagValidator;

    @Inject
    private OpenStackParameters openStackParameters;

    @Override
    public Platform platform() {
        return OpenStackConstants.OPENSTACK_PLATFORM;
    }

    @Override
    public Variant variant() {
        return OpenStackConstants.OpenStackVariant.HEAT.variant();
    }

    @Override
    public Authenticator authentication() {
        return authenticator;
    }

    @Override
    public ResourceConnector resources() {
        return resourceConnector;
    }

    @Override
    public InstanceConnector instances() {
        return instanceConnector;
    }

    @Override
    public MetadataCollector metadata() {
        return metadataCollector;
    }

    @Override
    public PlatformParameters parameters() {
        return openStackParameters;
    }

    @Override
    public Setup setup() {
        return openStackSetup;
    }

    @Override
    public List<Validator> validators() {
        return Collections.singletonList(openStackTagValidator);
    }

    @Override
    public CredentialConnector credentials() {
        return credentialConnector;
    }
}
