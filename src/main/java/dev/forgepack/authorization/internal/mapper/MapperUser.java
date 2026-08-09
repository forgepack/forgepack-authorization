package dev.forgepack.authorization.internal.mapper;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.payload.DTORequestUser;
import dev.forgepack.authorization.internal.payload.DTOResponseUser;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class MapperUser implements Mapper<User, DTORequestUser, DTOResponseUser> {

    private final MapperRole mapperRole;

    public MapperUser(MapperRole mapperRole) {
        this.mapperRole = mapperRole;
    }

    @Override
    public User toEntity(DTORequestUser dto) {
        if (dto == null) return null;
        return new User(
                dto.username(),
                dto.email(),
                mapperRole.toEntitySet(dto.role())
        );
    }

    @Override
    public DTOResponseUser toResponse(User entity) {
        if (entity == null) return null;
        return new DTOResponseUser(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getAttempt(),
                entity.getActive(),
                mapperRole.toResponseSet(entity.getRole())
        );
    }

    @Override
    public void updateEntity(DTORequestUser dto, User entity) {
        if (dto == null || entity == null) return;
        entity.setUsername(dto.username());
        entity.setEmail(dto.email());
        if (dto.role() != null) {
            entity.setRole(mapperRole.toEntitySet(dto.role()));
        }
    }

    @Override
    public Set<DTOResponseUser> toResponseSet(Set<User> entities) {
        if (entities == null) return Set.of();
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }
}
