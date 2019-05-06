package com.sequenceiq.cloudbreak.cmtemplate.configproviders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudera.api.swagger.model.ApiClusterTemplateConfig;
import com.cloudera.api.swagger.model.ApiClusterTemplateVariable;
import com.sequenceiq.cloudbreak.cloud.model.component.StackType;
import com.sequenceiq.cloudbreak.cmtemplate.CmTemplateComponentConfigProvider;
import com.sequenceiq.cloudbreak.cmtemplate.CmTemplateProcessor;
import com.sequenceiq.cloudbreak.template.TemplatePreparationObject;
import com.sequenceiq.cloudbreak.template.views.LdapView;

@Component
public class HdfsLdapConfigProvider implements CmTemplateComponentConfigProvider {

    @Override
    public List<ApiClusterTemplateConfig> getServiceConfigs(TemplatePreparationObject templatePreparationObject) {
        List<ApiClusterTemplateConfig> result = new ArrayList<>();
        result.add(new ApiClusterTemplateConfig().name("hadoop_security_group_mapping").variable("hdfs-hadoop_security_group_mapping"));
        result.add(new ApiClusterTemplateConfig().name("hadoop_group_mapping_ldap_url").variable("hdfs-hadoop_group_mapping_ldap_url"));
        result.add(new ApiClusterTemplateConfig().name("hadoop_group_mapping_ldap_bind_user").variable("hdfs-hadoop_group_mapping_ldap_bind_user"));
        result.add(new ApiClusterTemplateConfig().name("hadoop_group_mapping_ldap_bind_passwd").variable("hdfs-hadoop_group_mapping_ldap_bind_passwd"));
        // TODO Add core-site / hadoop.security.group.mapping.ldap.userbase cfg
        result.add(new ApiClusterTemplateConfig().name("hadoop_group_mapping_ldap_user_filter").variable("hdfs-hadoop_group_mapping_ldap_user_filter"));
        // TODO Add core-site / hadoop.security.group.mapping.ldap.groupbase cfg
        result.add(new ApiClusterTemplateConfig().name("hadoop_group_mapping_ldap_group_filter").variable("hdfs-hadoop_group_mapping_ldap_group_filter"));
        result.add(new ApiClusterTemplateConfig().name("hadoop_group_mapping_ldap_group_name_attr").variable("hdfs-hadoop_group_mapping_ldap_group_name_attr"));
        result.add(new ApiClusterTemplateConfig().name("hadoop_group_mapping_ldap_member_attr").variable("hdfs-hadoop_group_mapping_ldap_member_attr"));
        return result;
    }

    @Override
    public List<ApiClusterTemplateVariable> getServiceConfigVariables(TemplatePreparationObject source) {
        List<ApiClusterTemplateVariable> result = new ArrayList<>();
        LdapView ldapView = source.getLdapConfig().get();
        result.add(new ApiClusterTemplateVariable().name("hdfs-hadoop_security_group_mapping").value("org.apache.hadoop.security.LdapGroupsMapping"));
        result.add(new ApiClusterTemplateVariable().name("hdfs-hadoop_group_mapping_ldap_url").value(ldapView.getConnectionURL()));
        result.add(new ApiClusterTemplateVariable().name("hdfs-hadoop_group_mapping_ldap_bind_user").value(ldapView.getBindDn()));
        result.add(new ApiClusterTemplateVariable().name("hdfs-hadoop_group_mapping_ldap_bind_passwd").value(ldapView.getBindPassword()));
        // TODO Add core-site / hadoop.security.group.mapping.ldap.userbase var: ldap.userSearchBase
        result.add(new ApiClusterTemplateVariable().name("hdfs-hadoop_group_mapping_ldap_user_filter").value(
                String.format("(&(objectClass=%s)(%s={0}))", ldapView.getUserObjectClass(), ldapView.getUserNameAttribute())));
        // TODO Add core-site / hadoop.security.group.mapping.ldap.groupbase var: ldap.groupSearchBase
        result.add(new ApiClusterTemplateVariable().name("hdfs-hadoop_group_mapping_ldap_group_filter").value(
                String.format("(objectClass=%s)", ldapView.getGroupObjectClass())));
        result.add(new ApiClusterTemplateVariable().name("hdfs-hadoop_group_mapping_ldap_group_name_attr").value(ldapView.getGroupNameAttribute()));
        result.add(new ApiClusterTemplateVariable().name("hdfs-hadoop_group_mapping_ldap_member_attr").value(ldapView.getGroupMemberAttribute()));
        return result;
    }

    @Override
    public String getServiceType() {
        return "HDFS";
    }

    @Override
    public List<String> getRoleTypes() {
        return Arrays.asList("NAMENODE", "DATANODE", "SECONDARYNAMENODE");
    }

    @Override
    public boolean isConfigurationNeeded(CmTemplateProcessor cmTemplateProcessor, TemplatePreparationObject source) {
        return isNotHdfStack(cmTemplateProcessor) && source.getKerberosConfig().isEmpty() && source.getLdapConfig().isPresent()
                && cmTemplateProcessor.isRoleTypePresentInService(getServiceType(), getRoleTypes());
    }

    private boolean isNotHdfStack(CmTemplateProcessor cmTemplateProcessor) {
        return cmTemplateProcessor.getTemplate().getProducts().stream().filter(apv -> StackType.HDF.name().equals(apv.getProduct())).findFirst().isEmpty();
    }

}
