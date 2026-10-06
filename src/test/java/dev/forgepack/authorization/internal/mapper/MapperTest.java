package dev.forgepack.authorization.internal.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.UUID;

import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.payload.PrivilegeRequest;
import dev.forgepack.authorization.internal.payload.RoleRequest;
import dev.forgepack.authorization.internal.payload.UserRequest;
import dev.forgepack.authorization.internal.payload.PrivilegeResponse;
import dev.forgepack.authorization.internal.payload.RoleResponse;
import dev.forgepack.authorization.internal.payload.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapperTest {

    private PrivilegeMapper PrivilegeMapper;
    private RoleMapper RoleMapper;
    private UserMapper UserMapper;

    @BeforeEach
    void setUp() throws ReflectiveOperationException {
        Constructor<PrivilegeMapper> constructor = PrivilegeMapper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        PrivilegeMapper = constructor.newInstance();
        RoleMapper = new RoleMapper(PrivilegeMapper);
        UserMapper = new UserMapper(RoleMapper);
    }

    @Test
    void privilegeMapperConvertsValuesAndHandlesNullInputs() {
        assertNull(PrivilegeMapper.toEntity(null));
        assertNull(PrivilegeMapper.toResponse(null));
        assertTrue(PrivilegeMapper.toResponseSet(null).isEmpty());
        assertTrue(PrivilegeMapper.toEntitySet(null).isEmpty());

        Privilege privilege = PrivilegeMapper.toEntity(new PrivilegeRequest(null, "read"));
        assertEquals("read", privilege.getName());
        PrivilegeMapper.updateEntity(new PrivilegeRequest(null, "write"), privilege);
        assertEquals("write", privilege.getName());
        PrivilegeMapper.updateEntity(null, privilege);
        PrivilegeMapper.updateEntity(new PrivilegeRequest(null, "ignored"), null);
        assertEquals("write", privilege.getName());

        PrivilegeResponse response = PrivilegeMapper.toResponse(privilege);
        assertEquals("write", response.getName());
        assertEquals(1, PrivilegeMapper.toResponseSet(Set.of(privilege)).size());
        Set<Privilege> privileges = PrivilegeMapper.toEntitySet(Set.of(response));
        assertEquals("write", privileges.iterator().next().getName());
    }

    @Test
    void roleMapperConvertsValuesAndUpdatesOptionalPrivileges() {
        assertNull(RoleMapper.toEntity(null));
        assertNull(RoleMapper.toResponse(null));
        assertTrue(RoleMapper.toResponseSet(null).isEmpty());
        assertTrue(RoleMapper.toEntitySet(null).isEmpty());

        PrivilegeResponse responsePrivilege = new PrivilegeResponse(UUID.randomUUID(), "read");
        Role role = RoleMapper.toEntity(new RoleRequest(null, "admin", Set.of(responsePrivilege)));
        assertEquals("admin", role.getName());
        assertEquals("read", role.getPrivilege().iterator().next().getName());

        RoleMapper.updateEntity(new RoleRequest(null, "operator", null), role);
        assertEquals("operator", role.getName());
        assertEquals("read", role.getPrivilege().iterator().next().getName());
        RoleMapper.updateEntity(new RoleRequest(null, "editor", Set.of(responsePrivilege)), role);
        assertEquals("editor", role.getName());
        RoleMapper.updateEntity(null, role);
        RoleMapper.updateEntity(new RoleRequest(null, "ignored", null), null);
        assertEquals("editor", role.getName());

        RoleResponse responseRole = RoleMapper.toResponse(role);
        assertEquals("editor", responseRole.getName());
        assertEquals(1, responseRole.getDTOResponsePrivilege().size());
        assertEquals(1, RoleMapper.toResponseSet(Set.of(role)).size());
        Role convertedRole = RoleMapper.toEntitySet(Set.of(new RoleResponse(null, "viewer", Set.of()))).iterator().next();
        assertEquals("viewer", convertedRole.getName());
    }

    @Test
    void userMapperConvertsValuesAndUpdatesOptionalRoles() {
        assertNull(UserMapper.toEntity(null));
        assertNull(UserMapper.toResponse(null));
        assertTrue(UserMapper.toResponseSet(null).isEmpty());

        RoleResponse responseRole = new RoleResponse(null, "admin", Set.of());
        User user = UserMapper.toEntity(new UserRequest(null, "jane", "jane@example.test", Set.of(responseRole)));
        assertEquals("jane", user.getUsername());
        assertEquals("admin", user.getRole().iterator().next().getName());

        UserMapper.updateEntity(new UserRequest(null, "jane.doe", "jane@example.test", null), user);
        assertEquals("jane.doe", user.getUsername());
        assertEquals("admin", user.getRole().iterator().next().getName());
        UserMapper.updateEntity(new UserRequest(null, "jane", "jane.doe@example.test", Set.of(responseRole)), user);
        assertEquals("jane.doe@example.test", user.getEmail());
        UserMapper.updateEntity(null, user);
        UserMapper.updateEntity(new UserRequest(null, "ignored", "ignored@example.test", null), null);
        assertEquals("jane", user.getUsername());

        UserResponse responseUser = UserMapper.toResponse(user);
        assertEquals("jane", responseUser.getUsername());
        assertEquals(1, responseUser.getRole().size());
        assertEquals(1, UserMapper.toResponseSet(Set.of(user)).size());
    }
}