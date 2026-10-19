package dev.forgepack.authorization.internal.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import dev.forgepack.authorization.AuthorizationPackage;

@AutoConfiguration
@AutoConfigurationPackage(basePackageClasses = AuthorizationPackage.class)
public class AuthorizationAutoConfiguration {
}
