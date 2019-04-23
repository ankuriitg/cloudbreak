package com.sequenceiq.cloudbreak.repository;

import static com.sequenceiq.cloudbreak.authorization.ResourceAction.READ;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.repository.query.Param;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.ResourceStatus;
import com.sequenceiq.cloudbreak.aspect.DisableHasPermission;
import com.sequenceiq.cloudbreak.aspect.workspace.CheckPermissionsByReturnValue;
import com.sequenceiq.cloudbreak.aspect.workspace.CheckPermissionsByTarget;
import com.sequenceiq.cloudbreak.aspect.workspace.WorkspaceResourceType;
import com.sequenceiq.cloudbreak.authorization.WorkspaceResource;
import com.sequenceiq.cloudbreak.domain.Blueprint;
import com.sequenceiq.cloudbreak.repository.workspace.WorkspaceResourceRepository;
import com.sequenceiq.cloudbreak.service.EntityType;

@EntityType(entityClass = Blueprint.class)
@Transactional(TxType.REQUIRED)
@DisableHasPermission
@WorkspaceResourceType(resource = WorkspaceResource.BLUEPRINT)
public interface BlueprintRepository extends WorkspaceResourceRepository<Blueprint, Long> {

    @CheckPermissionsByReturnValue
    Optional<Blueprint> findByWorkspaceIdAndName(@Param("workspaceId") Long workspaceId, @Param("name") String name);

    @CheckPermissionsByReturnValue
    Set<Blueprint> findAllByWorkspaceIdAndStatus(Long workspaceId, ResourceStatus status);

    @Override
    @DisableHasPermission
    @CheckPermissionsByTarget(action = READ, targetIndex = 0)
    <S extends Blueprint> Iterable<S> saveAll(Iterable<S> entities);
}