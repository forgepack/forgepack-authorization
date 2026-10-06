package dev.forgepack.authorization.internal.service;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.core.api.repository.CrudRepository;
import dev.forgepack.validation.api.service.UniqueCheckableService;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.payload.RoleRequest;
import dev.forgepack.authorization.internal.payload.RoleResponse;
import dev.forgepack.authorization.internal.repository.RoleRepository;
import dev.forgepack.core.internal.service.RestorableServiceImpl;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RoleService extends RestorableServiceImpl<Role, RoleRequest, RoleResponse> implements UniqueCheckableService {

    private final RoleRepository roleRepository;

    public RoleService(CrudRepository<Role> repositoryGeneric, Mapper<Role, RoleRequest, RoleResponse> mapperInterface, RoleRepository roleRepository) {
        super(Role.class, repositoryGeneric, mapperInterface);
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return roleRepository.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return roleRepository.existsByNameIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }
}
