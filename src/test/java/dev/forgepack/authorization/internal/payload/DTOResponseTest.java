package dev.forgepack.authorization.internal.payload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class DTOResponseTest {

    @Test
    void privilegeResponseExposesIdAndName() {
        UUID id = UUID.randomUUID();
        PrivilegeResponse response = new PrivilegeResponse(id, "read");

        assertEquals(id, response.getId());
        assertEquals("read", response.getName());
        assertEquals(id, response.id());
    }

    @Test
    void roleResponseExposesIdNameAndPrivileges() {
        UUID id = UUID.randomUUID();
        PrivilegeResponse privilege = new PrivilegeResponse(UUID.randomUUID(), "read");
        RoleResponse response = new RoleResponse(id, "admin", Set.of(privilege));

        assertEquals(id, response.getId());
        assertEquals("admin", response.getName());
        assertEquals(id, response.id());
        assertTrue(response.getDTOResponsePrivilege().contains(privilege));
    }

    @Test
    void userResponseExposesAllFields() {
        UUID id = UUID.randomUUID();
        RoleResponse role = new RoleResponse(UUID.randomUUID(), "admin", Set.of());
        UserResponse response = new UserResponse(id, "jane", "jane@example.test", 3, true, Set.of(role));

        assertEquals(id, response.getId());
        assertEquals("jane", response.getUsername());
        assertEquals("jane@example.test", response.getEmail());
        assertEquals(3, response.getAttempt());
        assertTrue(response.getActive());
        assertEquals(id, response.id());
        assertTrue(response.getRole().contains(role));
    }
}
