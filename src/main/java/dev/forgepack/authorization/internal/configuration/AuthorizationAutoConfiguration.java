package dev.forgepack.authorization.internal.configuration;

import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.model.User;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;

@AutoConfiguration
@AutoConfigurationPackage(basePackageClasses = {Privilege.class, Role.class, User.class})
public class AuthorizationAutoConfiguration {
}
