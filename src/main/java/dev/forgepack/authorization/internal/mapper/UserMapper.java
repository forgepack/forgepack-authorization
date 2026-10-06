package dev.forgepack.authorization.internal.mapper;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.payload.UserRequest;
import dev.forgepack.authorization.internal.payload.UserResponse;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class UserMapper implements Mapper<User, UserRequest, UserResponse> {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper RoleMapper) {
        this.roleMapper = RoleMapper;
    }

    @Override
    public User toEntity(UserRequest dto) {
        if (dto == null) return null;
        return new User(
                dto.username(),
                dto.email(),
                roleMapper.toEntitySet(dto.role())
        );
    }

    @Override
    public UserResponse toResponse(User entity) {
        if (entity == null) return null;
        return new UserResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getAttempt(),
                entity.getActive(),
                roleMapper.toResponseSet(entity.getRole())
        );
    }

    @Override
    public void updateEntity(UserRequest dto, User entity) {
        if (dto == null || entity == null) return;
        entity.setUsername(dto.username());
        entity.setEmail(dto.email());
        if (dto.role() != null) {
            entity.setRole(roleMapper.toEntitySet(dto.role()));
        }
    }

    @Override
    public Set<UserResponse> toResponseSet(Set<User> entities) {
        if (entities == null) return Set.of();
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }
}
