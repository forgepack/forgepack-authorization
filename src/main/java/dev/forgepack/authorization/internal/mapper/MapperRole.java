package dev.forgepack.authorization.internal.mapper;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.payload.DTORequestRole;
import dev.forgepack.authorization.internal.payload.DTOResponseRole;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class MapperRole implements Mapper<Role, DTORequestRole, DTOResponseRole> {

    private final MapperPrivilege mapperPrivilege;

    public MapperRole(MapperPrivilege mapperPrivilege) {
        this.mapperPrivilege = mapperPrivilege;
    }

    @Override
    public Role toEntity(DTORequestRole dto) {
        if (dto == null) return null;
        return new Role(
                dto.name(),
                mapperPrivilege.toEntitySet(dto.privilege())
        );
    }

    @Override
    public DTOResponseRole toResponse(Role entity) {
        if (entity == null) return null;
        return new DTOResponseRole(
                entity.getId(),
                entity.getName(),
                mapperPrivilege.toResponseSet(entity.getPrivilege())
        );
    }

    @Override
    public void updateEntity(DTORequestRole dto, Role entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.name());
        if (dto.privilege() != null) {
            entity.setPrivilege(mapperPrivilege.toEntitySet(dto.privilege()));
        }
    }

    @Override
    public Set<DTOResponseRole> toResponseSet(Set<Role> entities) {
        if (entities == null) return Set.of();
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }

    /**
     * Converts a set of {@link DTOResponseRole} DTOs into a set of {@link Role} entities.
     *
     * @param dtos set of response DTOs to convert
     * @return set of entities, or an empty set if {@code dtos} is {@code null}
     */
    public Set<Role> toEntitySet(Set<DTOResponseRole> dtos) {
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
