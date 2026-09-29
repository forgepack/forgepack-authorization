package dev.forgepack.authorization.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.payload.DTORequestPrivilege;
import dev.forgepack.authorization.internal.payload.DTORequestRole;
import dev.forgepack.authorization.internal.payload.DTORequestUser;
import dev.forgepack.authorization.internal.payload.DTOResponsePrivilege;
import dev.forgepack.authorization.internal.payload.DTOResponseRole;
import dev.forgepack.authorization.internal.payload.DTOResponseUser;
import dev.forgepack.authorization.internal.repository.RepositoryPrivilege;
import dev.forgepack.authorization.internal.repository.RepositoryRole;
import dev.forgepack.authorization.internal.repository.RepositoryUser;
import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.core.api.repository.RepositoryCrud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceUniqueCheckableTest {

    private RepositoryRole repositoryRole;
    private RepositoryPrivilege repositoryPrivilege;
    private RepositoryUser repositoryUser;
    private RepositoryCrud<Role> repositoryCrudRole;
    private RepositoryCrud<Privilege> repositoryCrudPrivilege;
    private RepositoryCrud<User> repositoryCrudUser;
    private Mapper<Role, DTORequestRole, DTOResponseRole> mapperRole;
    private Mapper<Privilege, DTORequestPrivilege, DTOResponsePrivilege> mapperPrivilege;
    private Mapper<User, DTORequestUser, DTOResponseUser> mapperUser;
    private ServiceRole serviceRole;
    private ServicePrivilege servicePrivilege;
    private ServiceUser serviceUser;

    @BeforeEach
    void setUp() {
        repositoryRole = mock(RepositoryRole.class);
        repositoryPrivilege = mock(RepositoryPrivilege.class);
        repositoryUser = mock(RepositoryUser.class);
        repositoryCrudRole = mockGeneric(RepositoryCrud.class);
        repositoryCrudPrivilege = mockGeneric(RepositoryCrud.class);
        repositoryCrudUser = mockGeneric(RepositoryCrud.class);
        mapperRole = mockGeneric(Mapper.class);
        mapperPrivilege = mockGeneric(Mapper.class);
        mapperUser = mockGeneric(Mapper.class);

        serviceRole = new ServiceRole(
            repositoryCrudRole,
            mapperRole,
                repositoryRole);
        servicePrivilege = new ServicePrivilege(
            repositoryCrudPrivilege,
            mapperPrivilege,
                repositoryPrivilege);
        serviceUser = new ServiceUser(
            repositoryCrudUser,
            mapperUser,
                repositoryUser);
    }

        @SuppressWarnings("unchecked")
        private static <T> T mockGeneric(Class<?> type) {
        return (T) mock(type);
        }

    @Test
    void roleChecksNameAndNameExcludingId() {
        UUID id = UUID.randomUUID();
        when(repositoryRole.existsByNameIgnoreCase("admin")).thenReturn(true);
        when(repositoryRole.existsByNameIgnoreCaseAndIdNot("admin", id)).thenReturn(true);

        assertTrue(serviceRole.existsByField("name", "admin"));
        assertTrue(serviceRole.existsByFieldAndIdNot("name", "admin", id));
    }

    @Test
    void roleRejectsUnsupportedFields() {
        assertThrows(IllegalArgumentException.class, () -> serviceRole.existsByField("email", "a@b.test"));
        assertThrows(IllegalArgumentException.class, () -> serviceRole.existsByFieldAndIdNot("email", "a@b.test", UUID.randomUUID()));
    }

    @Test
    void privilegeChecksNameAndNameExcludingId() {
        UUID id = UUID.randomUUID();
        when(repositoryPrivilege.existsByNameIgnoreCase("read")).thenReturn(true);
        when(repositoryPrivilege.existsByNameIgnoreCaseAndIdNot("read", id)).thenReturn(true);

        assertTrue(servicePrivilege.existsByField("name", "read"));
        assertTrue(servicePrivilege.existsByFieldAndIdNot("name", "read", id));
    }

    @Test
    void privilegeRejectsUnsupportedFields() {
        assertThrows(IllegalArgumentException.class, () -> servicePrivilege.existsByField("email", "a@b.test"));
        assertThrows(IllegalArgumentException.class, () -> servicePrivilege.existsByFieldAndIdNot("email", "a@b.test", UUID.randomUUID()));
    }

    @Test
    void userChecksUsernameAndEmailWithAndWithoutId() {
        UUID id = UUID.randomUUID();
        when(repositoryUser.existsByUsernameIgnoreCase("jane")).thenReturn(true);
        when(repositoryUser.existsByEmailIgnoreCase("jane@example.test")).thenReturn(true);
        when(repositoryUser.existsByUsernameIgnoreCaseAndIdNot("jane", id)).thenReturn(true);
        when(repositoryUser.existsByEmailIgnoreCaseAndIdNot("jane@example.test", id)).thenReturn(true);

        assertTrue(serviceUser.existsByField("username", "jane"));
        assertTrue(serviceUser.existsByField("email", "jane@example.test"));
        assertTrue(serviceUser.existsByFieldAndIdNot("username", "jane", id));
        assertTrue(serviceUser.existsByFieldAndIdNot("email", "jane@example.test", id));
    }

    @Test
    void userRejectsUnsupportedFields() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> serviceUser.existsByField("name", "jane"));
        assertThrows(IllegalArgumentException.class, () -> serviceUser.existsByFieldAndIdNot("name", "jane", id));
    }

    @Test
    void securePasswordHasEightCharactersAndAllRequiredCharacterKinds() {
        String password = serviceUser.generateSecurePassword();

        assertEquals(8, password.length());
        assertTrue(password.chars().anyMatch(Character::isUpperCase));
        assertTrue(password.chars().anyMatch(Character::isLowerCase));
        assertTrue(password.chars().anyMatch(Character::isDigit));
        assertTrue(password.chars().anyMatch(character -> "!@#$%^&*()-_=+[]{}|;:,.<>?".indexOf(character) >= 0));
    }
}