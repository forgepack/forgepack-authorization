package dev.forgepack.authorization.internal.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    void userDefaultConstructorStartsWithEmptyRoleSet() {
        User user = new User();
        assertNotNull(user.getRole());
        assertTrue(user.getRole().isEmpty());
    }

    @Test
    void userFullConstructorAndAccessorsExposeAllFields() {
        Role role = new Role("admin", Set.of());
        User user = new User("jane", "jane@example.test", "P@ssw0rd!", 2, true, "secret", Set.of(role));

        assertEquals("jane", user.getUsername());
        assertEquals("jane@example.test", user.getEmail());
        assertEquals("P@ssw0rd!", user.getPassword());
        assertEquals(2, user.getAttempt());
        assertTrue(user.getActive());
        assertEquals("secret", user.getSecret());
        assertEquals(1, user.getRole().size());
    }

    @Test
    void userSettersUpdateSecurityRelatedFields() {
        User user = new User();

        user.setPassword("newPassword");
        user.setAttempt(5);
        user.setActive(false);
        user.setSecret("newSecret");

        assertEquals("newPassword", user.getPassword());
        assertEquals(5, user.getAttempt());
        assertEquals(false, user.getActive());
        assertEquals("newSecret", user.getSecret());
    }
}
