package dev.forgepack.authorization.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;

import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.payload.PrivilegeRequest;
import dev.forgepack.authorization.internal.payload.RoleRequest;
import dev.forgepack.authorization.internal.payload.UserRequest;
import dev.forgepack.authorization.internal.payload.PrivilegeResponse;
import dev.forgepack.authorization.internal.payload.RoleResponse;
import dev.forgepack.authorization.internal.payload.UserResponse;
import dev.forgepack.authorization.internal.repository.PrivilegeRepository;
import dev.forgepack.authorization.internal.repository.RoleRepository;
import dev.forgepack.authorization.internal.repository.UserRepository;
import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.core.api.repository.CrudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UniqueCheckableServiceTest {

    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;
    private UserRepository userRepository;
    private CrudRepository<Role> CrudRepositoryRole;
    private CrudRepository<Privilege> CrudRepositoryPrivilege;
    private CrudRepository<User> CrudRepositoryUser;
    private Mapper<Role, RoleRequest, RoleResponse> RoleMapper;
    private Mapper<Privilege, PrivilegeRequest, PrivilegeResponse> PrivilegeMapper;
    private Mapper<User, UserRequest, UserResponse> UserMapper;
    private RoleService roleService;
    private PrivilegeService PrivilegeService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        roleRepository = mock(RoleRepository.class);
        privilegeRepository = mock(PrivilegeRepository.class);
        userRepository = mock(UserRepository.class);
        CrudRepositoryRole = mockGeneric(CrudRepository.class);
        CrudRepositoryPrivilege = mockGeneric(CrudRepository.class);
        CrudRepositoryUser = mockGeneric(CrudRepository.class);
        RoleMapper = mockGeneric(Mapper.class);
        PrivilegeMapper = mockGeneric(Mapper.class);
        UserMapper = mockGeneric(Mapper.class);

        roleService = new RoleService(
            CrudRepositoryRole,
            RoleMapper,
                roleRepository);
        PrivilegeService = new PrivilegeService(
            CrudRepositoryPrivilege,
            PrivilegeMapper,
                privilegeRepository);
        userService = new UserService(
            CrudRepositoryUser,
            UserMapper,
                userRepository);
    }

        @SuppressWarnings("unchecked")
        private static <T> T mockGeneric(Class<?> type) {
        return (T) mock(type);
        }

    @Test
    void roleChecksNameAndNameExcludingId() {
        UUID id = UUID.randomUUID();
        when(roleRepository.existsByNameIgnoreCase("admin")).thenReturn(true);
        when(roleRepository.existsByNameIgnoreCaseAndIdNot("admin", id)).thenReturn(true);

        assertTrue(roleService.existsByField("name", "admin"));
        assertTrue(roleService.existsByFieldAndIdNot("name", "admin", id));
    }

    @Test
    void roleRejectsUnsupportedFields() {
        assertThrows(IllegalArgumentException.class, () -> roleService.existsByField("email", "a@b.test"));
        assertThrows(IllegalArgumentException.class, () -> roleService.existsByFieldAndIdNot("email", "a@b.test", UUID.randomUUID()));
    }

    @Test
    void privilegeChecksNameAndNameExcludingId() {
        UUID id = UUID.randomUUID();
        when(privilegeRepository.existsByNameIgnoreCase("read")).thenReturn(true);
        when(privilegeRepository.existsByNameIgnoreCaseAndIdNot("read", id)).thenReturn(true);

        assertTrue(PrivilegeService.existsByField("name", "read"));
        assertTrue(PrivilegeService.existsByFieldAndIdNot("name", "read", id));
    }

    @Test
    void privilegeRejectsUnsupportedFields() {
        assertThrows(IllegalArgumentException.class, () -> PrivilegeService.existsByField("email", "a@b.test"));
        assertThrows(IllegalArgumentException.class, () -> PrivilegeService.existsByFieldAndIdNot("email", "a@b.test", UUID.randomUUID()));
    }

    @Test
    void userChecksUsernameAndEmailWithAndWithoutId() {
        UUID id = UUID.randomUUID();
        when(userRepository.existsByUsernameIgnoreCase("jane")).thenReturn(true);
        when(userRepository.existsByEmailIgnoreCase("jane@example.test")).thenReturn(true);
        when(userRepository.existsByUsernameIgnoreCaseAndIdNot("jane", id)).thenReturn(true);
        when(userRepository.existsByEmailIgnoreCaseAndIdNot("jane@example.test", id)).thenReturn(true);

        assertTrue(userService.existsByField("username", "jane"));
        assertTrue(userService.existsByField("email", "jane@example.test"));
        assertTrue(userService.existsByFieldAndIdNot("username", "jane", id));
        assertTrue(userService.existsByFieldAndIdNot("email", "jane@example.test", id));
    }

    @Test
    void userRejectsUnsupportedFields() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> userService.existsByField("name", "jane"));
        assertThrows(IllegalArgumentException.class, () -> userService.existsByFieldAndIdNot("name", "jane", id));
    }

    @Test
    void securePasswordHasEightCharactersAndAllRequiredCharacterKinds() {
        String password = userService.generateSecurePassword();

        assertEquals(8, password.length());
        assertTrue(password.chars().anyMatch(Character::isUpperCase));
        assertTrue(password.chars().anyMatch(Character::isLowerCase));
        assertTrue(password.chars().anyMatch(Character::isDigit));
        assertTrue(password.chars().anyMatch(character -> "!@#$%^&*()-_=+[]{}|;:,.<>?".indexOf(character) >= 0));
    }

    @Test
    void isValidToChangeByIdAlwaysThrowsWhenUserExists() {
        UUID id = UUID.randomUUID();
        User user = new User("jane", "jane@example.test", Set.of());
        when(userRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class, () -> userService.isValidToChange(id));
    }

    @Test
    void isValidToChangeByIdThrowsWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.isValidToChange(id));
    }

    @Test
    void isValidToChangeByUsernameAlwaysThrowsWhenUserExists() {
        User user = new User("jane", "jane@example.test", Set.of());
        when(userRepository.findByUsername("jane")).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class, () -> userService.isValidToChange("jane"));
    }

    @Test
    void isValidToChangeByUsernameThrowsWhenUserNotFound() {
        when(userRepository.findByUsername("jane")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.isValidToChange("jane"));
    }
}