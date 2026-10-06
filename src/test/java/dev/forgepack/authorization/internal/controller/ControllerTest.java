package dev.forgepack.authorization.internal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import dev.forgepack.authorization.internal.payload.PrivilegeResponse;
import dev.forgepack.authorization.internal.payload.RoleResponse;
import dev.forgepack.authorization.internal.payload.UserResponse;
import dev.forgepack.authorization.internal.service.PrivilegeService;
import dev.forgepack.authorization.internal.service.RoleService;
import dev.forgepack.authorization.internal.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ControllerTest {

    @Test
    void privilegeControllerDelegatesHardDeleteAndRestoreToService() {
        PrivilegeService PrivilegeService = mock(PrivilegeService.class);
        ControllerPrivilege controller = new ControllerPrivilege(PrivilegeService);
        UUID id = UUID.randomUUID();
        PrivilegeResponse response = new PrivilegeResponse(id, "read");
        when(PrivilegeService.restore(id)).thenReturn(response);

        ResponseEntity<Void> hardDeleteResponse = controller.hardDelete(id);
        verify(PrivilegeService).hardDelete(eq(id));
        assertEquals(HttpStatus.NO_CONTENT, hardDeleteResponse.getStatusCode());

        ResponseEntity<PrivilegeResponse> restoreResponse = controller.restore(id);
        assertEquals(HttpStatus.ACCEPTED, restoreResponse.getStatusCode());
        assertSame(response, restoreResponse.getBody());
    }

    @Test
    void roleControllerDelegatesHardDeleteAndRestoreToService() {
        RoleService roleService = mock(RoleService.class);
        ControllerRole controller = new ControllerRole(roleService);
        UUID id = UUID.randomUUID();
        RoleResponse response = new RoleResponse(id, "admin", null);
        when(roleService.restore(id)).thenReturn(response);

        ResponseEntity<Void> hardDeleteResponse = controller.hardDelete(id);
        verify(roleService).hardDelete(eq(id));
        assertEquals(HttpStatus.NO_CONTENT, hardDeleteResponse.getStatusCode());

        ResponseEntity<RoleResponse> restoreResponse = controller.restore(id);
        assertEquals(HttpStatus.ACCEPTED, restoreResponse.getStatusCode());
        assertSame(response, restoreResponse.getBody());
    }

    @Test
    void userControllerDelegatesHardDeleteAndRestoreToService() {
        UserService userService = mock(UserService.class);
        ControllerUser controller = new ControllerUser(userService);
        UUID id = UUID.randomUUID();
        UserResponse response = new UserResponse(id, "jane", "jane@example.test", 0, true, null);
        when(userService.restore(id)).thenReturn(response);

        ResponseEntity<Void> hardDeleteResponse = controller.hardDelete(id);
        verify(userService).hardDelete(eq(id));
        assertEquals(HttpStatus.NO_CONTENT, hardDeleteResponse.getStatusCode());

        ResponseEntity<UserResponse> restoreResponse = controller.restore(id);
        assertEquals(HttpStatus.ACCEPTED, restoreResponse.getStatusCode());
        assertSame(response, restoreResponse.getBody());
    }
}
