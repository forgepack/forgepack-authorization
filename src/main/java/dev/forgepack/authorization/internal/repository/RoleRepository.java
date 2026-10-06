package dev.forgepack.authorization.internal.repository;

import dev.forgepack.core.api.repository.CrudWithNameRepository;
import dev.forgepack.authorization.internal.model.Role;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends CrudWithNameRepository<Role> {

    Optional<Role> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
