package dev.forgepack.authorization.internal.mapper;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.payload.DTORequestPrivilege;
import dev.forgepack.authorization.internal.payload.DTOResponsePrivilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class MapperPrivilege implements Mapper<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {

    private static final Logger log = LoggerFactory.getLogger(MapperPrivilege.class);
    private MapperPrivilege() {}

    @Override
    public Privilege toEntity(DTORequestPrivilege dto) {
        if (dto == null) return null;
        return new Privilege(dto.name());
    }

    @Override
    public DTOResponsePrivilege toResponse(Privilege entity) {
        if (entity == null) return null;
        return new DTOResponsePrivilege(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public void updateEntity(DTORequestPrivilege dto, Privilege entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.name());
    }

    @Override
    public Set<DTOResponsePrivilege> toResponseSet(Set<Privilege> entities) {
        if (entities == null) return Set.of();
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }

    /**
     * Converts a set of {@link DTOResponsePrivilege} DTOs into a set of {@link Privilege} entities.
     *
     * @param dtos set of response DTOs to convert
     * @return set of entities, or an empty set if {@code dtos} is {@code null}
     */
    public Set<Privilege> toEntitySet(Set<DTOResponsePrivilege> dtos) {
        if (dtos == null) return Set.of();
        return dtos.stream()
                .map(dto -> {
                    Privilege privilege = new Privilege();
                    privilege.setName(dto.getName());
                    return privilege;
                })
                .collect(Collectors.toSet());
    }
}
