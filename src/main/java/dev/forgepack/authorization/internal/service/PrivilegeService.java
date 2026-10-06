package dev.forgepack.authorization.internal.service;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.core.api.repository.CrudRepository;
import dev.forgepack.validation.api.service.UniqueCheckableService;
import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.payload.PrivilegeRequest;
import dev.forgepack.authorization.internal.payload.PrivilegeResponse;
import dev.forgepack.authorization.internal.repository.PrivilegeRepository;
import dev.forgepack.core.internal.service.RestorableServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PrivilegeService extends RestorableServiceImpl<Privilege, PrivilegeRequest, PrivilegeResponse> implements UniqueCheckableService {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeService(CrudRepository<Privilege> repositoryGeneric, Mapper<Privilege, PrivilegeRequest, PrivilegeResponse> mapperInterface, PrivilegeRepository privilegeRepository) {
        super(Privilege.class, repositoryGeneric, mapperInterface);
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return privilegeRepository.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return privilegeRepository.existsByNameIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }
}
