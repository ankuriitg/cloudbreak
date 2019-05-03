package com.sequenceiq.environment.cloud;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.notification.model.ResourceNotification;
import com.sequenceiq.cloudbreak.cloud.service.Persister;

@Component
public class CloudResourcePersisterService implements Persister<ResourceNotification> {

    @Override
    public ResourceNotification persist(ResourceNotification data) {
        return data;
    }

    @Override
    public ResourceNotification update(ResourceNotification data) {
        return data;
    }

    @Override
    public ResourceNotification retrieve(ResourceNotification data) {
        return data;
    }

    @Override
    public ResourceNotification delete(ResourceNotification data) {
        return data;
    }
}
