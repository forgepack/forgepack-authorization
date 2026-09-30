package dev.forgepack.authorization.internal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import dev.forgepack.authorization.internal.payload.DTOResponsePrivilege;
import dev.forgepack.authorization.internal.payload.DTOResponseRole;
import dev.forgepack.authorization.internal.payload.DTOResponseUser;
import dev.forgepack.authorization.internal.service.ServicePrivilege;
import dev.forgepack.authorization.internal.service.ServiceRole;
import dev.forgepack.authorization.internal.service.ServiceUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ControllerTest {

    @Test
    void privilegeControllerDelegatesHardDeleteAndRestoreToService() {
        ServicePrivilege servicePrivilege = mock(ServicePrivilege.class);
        ControllerPrivilege controller = new ControllerPrivilege(servicePrivilege);
        UUID id = UUID.randomUUID();
        DTOResponsePrivilege response = new DTOResponsePrivilege(id, "read");
        when(servicePrivilege.restore(id)).thenReturn(response);

        ResponseEntity<Void> hardDeleteResponse = controller.hardDelete(id);
        verify(servicePrivilege).hardDelete(eq(id));
        assertEquals(HttpStatus.NO_CONTENT, hardDeleteResponse.getStatusCode());

        ResponseEntity<DTOResponsePrivilege> restoreResponse = controller.restore(id);
        assertEquals(HttpStatus.ACCEPTED, restoreResponse.getStatusCode());
        assertSame(response, restoreResponse.getBody());
    }

    @Test
    void roleControllerDelegatesHardDeleteAndRestoreToService() {
        ServiceRole serviceRole = mock(ServiceRole.class);
        ControllerRole controller = new ControllerRole(serviceRole);
        UUID id = UUID.randomUUID();
        DTOResponseRole response = new DTOResponseRole(id, "admin", null);
        when(serviceRole.restore(id)).thenReturn(response);

        ResponseEntity<Void> hardDeleteResponse = controller.hardDelete(id);
        verify(serviceRole).hardDelete(eq(id));
        assertEquals(HttpStatus.NO_CONTENT, hardDeleteResponse.getStatusCode());

        ResponseEntity<DTOResponseRole> restoreResponse = controller.restore(id);
        assertEquals(HttpStatus.ACCEPTED, restoreResponse.getStatusCode());
        assertSame(response, restoreResponse.getBody());
    }

    @Test
    void userControllerDelegatesHardDeleteAndRestoreToService() {
        ServiceUser serviceUser = mock(ServiceUser.class);
        ControllerUser controller = new ControllerUser(serviceUser);
        UUID id = UUID.randomUUID();
        DTOResponseUser response = new DTOResponseUser(id, "jane", "jane@example.test", 0, true, null);
        when(serviceUser.restore(id)).thenReturn(response);

        ResponseEntity<Void> hardDeleteResponse = controller.hardDelete(id);
        verify(serviceUser).hardDelete(eq(id));
        assertEquals(HttpStatus.NO_CONTENT, hardDeleteResponse.getStatusCode());

        ResponseEntity<DTOResponseUser> restoreResponse = controller.restore(id);
        assertEquals(HttpStatus.ACCEPTED, restoreResponse.getStatusCode());
        assertSame(response, restoreResponse.getBody());
    }
}
