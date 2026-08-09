package dev.forgepack.authorization.internal.repository;

import dev.forgepack.core.api.repository.RepositoryGeneric;
import dev.forgepack.authorization.internal.model.User;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryUser extends RepositoryGeneric<User> {

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.role r LEFT JOIN FETCH r.privilege WHERE u.username = :name")
    Optional<User> findByUsername(String name);
    boolean existsByUsernameIgnoreCase(String name);
    boolean existsByUsernameIgnoreCaseAndIdNot(String name, UUID id);
    boolean existsByEmailIgnoreCase(String name);
    boolean existsByEmailIgnoreCaseAndIdNot(String name, UUID id);
}

