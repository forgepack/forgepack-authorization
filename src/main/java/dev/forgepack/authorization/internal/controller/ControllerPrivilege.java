package dev.forgepack.authorization.internal.controller;

import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.payload.PrivilegeRequest;
import dev.forgepack.authorization.internal.payload.PrivilegeResponse;
import dev.forgepack.authorization.internal.service.PrivilegeService;
import dev.forgepack.core.internal.controller.RestorableControllerImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;

@RestController
@RequestMapping("/privilege")
public class ControllerPrivilege extends RestorableControllerImpl<Privilege, PrivilegeRequest, PrivilegeResponse> {

    private final PrivilegeService privilegeService;

    public ControllerPrivilege(PrivilegeService PrivilegeService) {
        super(Privilege.class, PrivilegeService);
        this.privilegeService = PrivilegeService;
    }

//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id){
        privilegeService.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<PrivilegeResponse> restore(@PathVariable UUID id){
        return ResponseEntity.accepted().body(privilegeService.restore(id));
    }
}
