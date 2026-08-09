package dev.forgepack.authorization.internal.repository;

import dev.forgepack.core.api.repository.RepositoryGenericWithName;
import dev.forgepack.authorization.internal.model.Privilege;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryPrivilege extends RepositoryGenericWithName<Privilege> {

    Optional<Privilege> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
