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
import dev.forgepack.authorization.internal.payload.DTORequestPrivilege;
import dev.forgepack.authorization.internal.payload.DTORequestRole;
import dev.forgepack.authorization.internal.payload.DTORequestUser;
import dev.forgepack.authorization.internal.payload.DTOResponsePrivilege;
import dev.forgepack.authorization.internal.payload.DTOResponseRole;
import dev.forgepack.authorization.internal.payload.DTOResponseUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapperTest {

    private MapperPrivilege mapperPrivilege;
    private MapperRole mapperRole;
    private MapperUser mapperUser;

    @BeforeEach
    void setUp() throws ReflectiveOperationException {
        Constructor<MapperPrivilege> constructor = MapperPrivilege.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        mapperPrivilege = constructor.newInstance();
        mapperRole = new MapperRole(mapperPrivilege);
        mapperUser = new MapperUser(mapperRole);
    }

    @Test
    void privilegeMapperConvertsValuesAndHandlesNullInputs() {
        assertNull(mapperPrivilege.toEntity(null));
        assertNull(mapperPrivilege.toResponse(null));
        assertTrue(mapperPrivilege.toResponseSet(null).isEmpty());
        assertTrue(mapperPrivilege.toEntitySet(null).isEmpty());

        Privilege privilege = mapperPrivilege.toEntity(new DTORequestPrivilege(null, "read"));
        assertEquals("read", privilege.getName());
        mapperPrivilege.updateEntity(new DTORequestPrivilege(null, "write"), privilege);
        assertEquals("write", privilege.getName());
        mapperPrivilege.updateEntity(null, privilege);
        mapperPrivilege.updateEntity(new DTORequestPrivilege(null, "ignored"), null);
        assertEquals("write", privilege.getName());

        DTOResponsePrivilege response = mapperPrivilege.toResponse(privilege);
        assertEquals("write", response.getName());
        assertEquals(1, mapperPrivilege.toResponseSet(Set.of(privilege)).size());
        Set<Privilege> privileges = mapperPrivilege.toEntitySet(Set.of(response));
        assertEquals("write", privileges.iterator().next().getName());
    }

    @Test
    void roleMapperConvertsValuesAndUpdatesOptionalPrivileges() {
        assertNull(mapperRole.toEntity(null));
        assertNull(mapperRole.toResponse(null));
        assertTrue(mapperRole.toResponseSet(null).isEmpty());
        assertTrue(mapperRole.toEntitySet(null).isEmpty());

        DTOResponsePrivilege responsePrivilege = new DTOResponsePrivilege(UUID.randomUUID(), "read");
        Role role = mapperRole.toEntity(new DTORequestRole(null, "admin", Set.of(responsePrivilege)));
        assertEquals("admin", role.getName());
        assertEquals("read", role.getPrivilege().iterator().next().getName());

        mapperRole.updateEntity(new DTORequestRole(null, "operator", null), role);
        assertEquals("operator", role.getName());
        assertEquals("read", role.getPrivilege().iterator().next().getName());
        mapperRole.updateEntity(new DTORequestRole(null, "editor", Set.of(responsePrivilege)), role);
        assertEquals("editor", role.getName());
        mapperRole.updateEntity(null, role);
        mapperRole.updateEntity(new DTORequestRole(null, "ignored", null), null);
        assertEquals("editor", role.getName());

        DTOResponseRole responseRole = mapperRole.toResponse(role);
        assertEquals("editor", responseRole.getName());
        assertEquals(1, responseRole.getDTOResponsePrivilege().size());
        assertEquals(1, mapperRole.toResponseSet(Set.of(role)).size());
        Role convertedRole = mapperRole.toEntitySet(Set.of(new DTOResponseRole(null, "viewer", Set.of()))).iterator().next();
        assertEquals("viewer", convertedRole.getName());
    }

    @Test
    void userMapperConvertsValuesAndUpdatesOptionalRoles() {
        assertNull(mapperUser.toEntity(null));
        assertNull(mapperUser.toResponse(null));
        assertTrue(mapperUser.toResponseSet(null).isEmpty());

        DTOResponseRole responseRole = new DTOResponseRole(null, "admin", Set.of());
        User user = mapperUser.toEntity(new DTORequestUser(null, "jane", "jane@example.test", Set.of(responseRole)));
        assertEquals("jane", user.getUsername());
        assertEquals("admin", user.getRole().iterator().next().getName());

        mapperUser.updateEntity(new DTORequestUser(null, "jane.doe", "jane@example.test", null), user);
        assertEquals("jane.doe", user.getUsername());
        assertEquals("admin", user.getRole().iterator().next().getName());
        mapperUser.updateEntity(new DTORequestUser(null, "jane", "jane.doe@example.test", Set.of(responseRole)), user);
        assertEquals("jane.doe@example.test", user.getEmail());
        mapperUser.updateEntity(null, user);
        mapperUser.updateEntity(new DTORequestUser(null, "ignored", "ignored@example.test", null), null);
        assertEquals("jane", user.getUsername());

        DTOResponseUser responseUser = mapperUser.toResponse(user);
        assertEquals("jane", responseUser.getUsername());
        assertEquals(1, responseUser.getRole().size());
        assertEquals(1, mapperUser.toResponseSet(Set.of(user)).size());
    }
}