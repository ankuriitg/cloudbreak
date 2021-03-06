package com.sequenceiq.redbeams.service.ha;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedbeamsNodeConfig {

    @Value("${redbeams.instance.node.id:}")
    private String id;

    public String getId() {
        return isNodeIdSpecified() ? id : null;
    }

    public boolean isNodeIdSpecified() {
        return StringUtils.isNoneBlank(id);
    }
}
