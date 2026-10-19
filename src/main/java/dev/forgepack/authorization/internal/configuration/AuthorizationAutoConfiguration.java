package dev.forgepack.authorization.internal.configuration;

import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.repository.PrivilegeRepository;
import dev.forgepack.authorization.internal.repository.RoleRepository;
import dev.forgepack.authorization.internal.repository.UserRepository;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@AutoConfigurationPackage(basePackageClasses = {Privilege.class, Role.class, User.class})
@EnableJpaRepositories(basePackageClasses = {
        PrivilegeRepository.class,
        RoleRepository.class,
        UserRepository.class
})
public class AuthorizationAutoConfiguration {
}
