package dev.forgepack.authorization.internal.repository;

import dev.forgepack.core.api.repository.CrudWithNameRepository;
import dev.forgepack.authorization.internal.model.Privilege;
import java.util.Optional;
import java.util.UUID;

public interface PrivilegeRepository extends CrudWithNameRepository<Privilege> {

    Optional<Privilege> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
