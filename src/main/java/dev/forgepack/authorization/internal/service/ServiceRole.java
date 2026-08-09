package dev.forgepack.authorization.internal.service;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.core.api.repository.RepositoryGeneric;
import dev.forgepack.validation.api.service.ServiceUniqueCheckable;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.payload.DTORequestRole;
import dev.forgepack.authorization.internal.payload.DTOResponseRole;
import dev.forgepack.authorization.internal.repository.RepositoryRole;
import dev.forgepack.core.internal.service.ServiceGenericImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ServiceRole extends ServiceGenericImpl<Role, DTORequestRole, DTOResponseRole> implements ServiceUniqueCheckable {

    private final RepositoryRole repositoryRole;

    public ServiceRole(RepositoryGeneric<Role> repositoryGeneric, Mapper<Role, DTORequestRole, DTOResponseRole> mapperInterface, RepositoryRole repositoryRole) {
        super(Role.class, repositoryGeneric, mapperInterface);
        this.repositoryRole = repositoryRole;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return repositoryRole.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return repositoryRole.existsByNameIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }
}
