package dev.forgepack.authorization.internal.mapper;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.payload.RoleRequest;
import dev.forgepack.authorization.internal.payload.RoleResponse;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class RoleMapper implements Mapper<Role, RoleRequest, RoleResponse> {

    private final PrivilegeMapper privilegeMapper;

    public RoleMapper(PrivilegeMapper PrivilegeMapper) {
        this.privilegeMapper = PrivilegeMapper;
    }

    @Override
    public Role toEntity(RoleRequest dto) {
        if (dto == null) return null;
        return new Role(
                dto.name(),
                privilegeMapper.toEntitySet(dto.privilege())
        );
    }

    @Override
    public RoleResponse toResponse(Role entity) {
        if (entity == null) return null;
        return new RoleResponse(
                entity.getId(),
                entity.getName(),
                privilegeMapper.toResponseSet(entity.getPrivilege())
        );
    }

    @Override
    public void updateEntity(RoleRequest dto, Role entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.name());
        if (dto.privilege() != null) {
            entity.setPrivilege(privilegeMapper.toEntitySet(dto.privilege()));
        }
    }

    @Override
    public Set<RoleResponse> toResponseSet(Set<Role> entities) {
        if (entities == null) return Set.of();
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }

    /**
     * Converts a set of {@link RoleResponse} DTOs into a set of {@link Role} entities.
     *
     * @param dtos set of response DTOs to convert
     * @return set of entities, or an empty set if {@code dtos} is {@code null}
     */
    public Set<Role> toEntitySet(Set<RoleResponse> dtos) {
        if (dtos == null) return Set.of();
        return dtos.stream()
                .map(dto -> {
                    Role role = new Role();
                    role.setName(dto.getName());
                    return role;
                })
                .collect(Collectors.toSet());
    }
}
