package dev.forgepack.authorization.internal.controller;

import dev.forgepack.core.api.controller.ControllerLifecycle;
import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.payload.DTORequestPrivilege;
import dev.forgepack.authorization.internal.payload.DTOResponsePrivilege;
import dev.forgepack.authorization.internal.service.ServicePrivilege;
import dev.forgepack.core.internal.controller.ControllerGenericImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;

@RestController
@RequestMapping("/privilege")
public class ControllerPrivilege extends ControllerGenericImpl<Privilege, DTORequestPrivilege, DTOResponsePrivilege> implements ControllerLifecycle<DTOResponsePrivilege> {

    private final ServicePrivilege servicePrivilege;

    public ControllerPrivilege(ServicePrivilege servicePrivilege) {
        super(Privilege.class, servicePrivilege);
        this.servicePrivilege = servicePrivilege;
    }

//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id){
        servicePrivilege.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<DTOResponsePrivilege> restore(@PathVariable UUID id){
        return ResponseEntity.accepted().body(servicePrivilege.restore(id));
    }
}
