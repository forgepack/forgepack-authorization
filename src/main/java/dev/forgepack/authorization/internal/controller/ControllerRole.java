package dev.forgepack.authorization.internal.controller;

import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.payload.DTORequestRole;
import dev.forgepack.authorization.internal.payload.DTOResponseRole;
import dev.forgepack.authorization.internal.service.ServiceRole;
import dev.forgepack.core.internal.controller.ControllerCrudRestorableImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;

@RestController
@RequestMapping("/role")
public class ControllerRole extends ControllerCrudRestorableImpl<Role, DTORequestRole, DTOResponseRole> {

    private final ServiceRole serviceRole;

    public ControllerRole(ServiceRole serviceRole) {
        super(Role.class, serviceRole);
        this.serviceRole = serviceRole;
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id){
        serviceRole.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<DTOResponseRole> restore(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceRole.restore(id));
    }
}
