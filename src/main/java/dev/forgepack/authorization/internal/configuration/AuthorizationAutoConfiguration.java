package dev.forgepack.authorization.internal.configuration;

import dev.forgepack.authorization.AuthorizationPackage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@AutoConfigurationPackage(basePackageClasses = AuthorizationPackage.class)
@ComponentScan(basePackageClasses = AuthorizationPackage.class)
@EntityScan(basePackageClasses = AuthorizationPackage.class)
@EnableJpaRepositories(basePackageClasses = AuthorizationPackage.class)
public class AuthorizationAutoConfiguration {
}
