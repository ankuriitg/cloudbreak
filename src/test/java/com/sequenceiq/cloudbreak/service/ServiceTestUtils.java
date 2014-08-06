package com.sequenceiq.cloudbreak.service;

import com.sequenceiq.cloudbreak.domain.Blueprint;
import com.sequenceiq.cloudbreak.domain.Company;
import com.sequenceiq.cloudbreak.domain.Stack;
import com.sequenceiq.cloudbreak.domain.User;
import com.sequenceiq.cloudbreak.domain.UserRole;

public final class ServiceTestUtils {

    private ServiceTestUtils() {
    }

    public static User createUser(UserRole role, Company company, Long userId) {
        User usr = new User();
        usr.setId(userId);
        usr.setCompany(company);
        usr.getUserRoles().add(role);
        return usr;
    }

    public static Company createCompany(String name, Long companyId) {
        Company company = new Company();
        company.setName(name);
        company.setId(companyId);
        return company;
    }

    public static Blueprint createBlueprint(User bpUser) {
        Blueprint blueprint = new Blueprint();
        blueprint.setId(1L);
        blueprint.setUser(bpUser);
        blueprint.setBlueprintName("dummyName");
        blueprint.setBlueprintText("dummyText");
        blueprint.getUserRoles().addAll(bpUser.getUserRoles());
        return blueprint;
    }

    public static Stack createStack(User user) {
        Stack stack = new Stack();
        stack.setUser(user);
        stack.getUserRoles().addAll(user.getUserRoles());
        stack.setTerminated(Boolean.FALSE);
        return stack;
    }

}
