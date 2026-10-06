package dev.forgepack.authorization.internal.controller;

import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.payload.RoleRequest;
import dev.forgepack.authorization.internal.payload.RoleResponse;
import dev.forgepack.authorization.internal.service.RoleService;
import dev.forgepack.core.internal.controller.RestorableControllerImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;

@RestController
@RequestMapping("/role")
public class ControllerRole extends RestorableControllerImpl<Role, RoleRequest, RoleResponse> {

    private final RoleService roleService;

    public ControllerRole(RoleService roleService) {
        super(Role.class, roleService);
        this.roleService = roleService;
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id){
        roleService.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<RoleResponse> restore(@PathVariable UUID id){
        return ResponseEntity.accepted().body(roleService.restore(id));
    }
}
