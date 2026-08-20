package dev.forgepack.authorization.internal.service;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.core.api.repository.RepositoryCrud;
import dev.forgepack.validation.api.service.ServiceUniqueCheckable;
import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.payload.DTORequestPrivilege;
import dev.forgepack.authorization.internal.payload.DTOResponsePrivilege;
import dev.forgepack.authorization.internal.repository.RepositoryPrivilege;
import dev.forgepack.core.internal.service.ServiceCrudRestorableImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ServicePrivilege extends ServiceCrudRestorableImpl<Privilege, DTORequestPrivilege, DTOResponsePrivilege> implements ServiceUniqueCheckable {

    private final RepositoryPrivilege repositoryPrivilege;

    public ServicePrivilege(RepositoryCrud<Privilege> repositoryGeneric, Mapper<Privilege, DTORequestPrivilege, DTOResponsePrivilege> mapperInterface, RepositoryPrivilege repositoryPrivilege) {
        super(Privilege.class, repositoryGeneric, mapperInterface);
        this.repositoryPrivilege = repositoryPrivilege;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return repositoryPrivilege.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return repositoryPrivilege.existsByNameIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }
}
