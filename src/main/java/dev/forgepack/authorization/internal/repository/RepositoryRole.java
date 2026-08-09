package dev.forgepack.authorization.internal.repository;

import dev.forgepack.core.api.repository.RepositoryGenericWithName;
import dev.forgepack.authorization.internal.model.Role;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryRole extends RepositoryGenericWithName<Role> {

    Optional<Role> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
