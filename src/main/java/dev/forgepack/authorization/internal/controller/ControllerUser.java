package dev.forgepack.authorization.internal.controller;

import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.payload.DTORequestUser;
import dev.forgepack.authorization.internal.payload.DTOResponseUser;
import dev.forgepack.authorization.internal.service.ServiceUser;
import dev.forgepack.core.internal.controller.ControllerCrudRestorableImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class ControllerUser extends ControllerCrudRestorableImpl<User, DTORequestUser, DTOResponseUser> {

    private final ServiceUser serviceUser;

    public ControllerUser(ServiceUser serviceUser) {
        super(User.class, serviceUser);
        this.serviceUser = serviceUser;
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id){
        serviceUser.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<DTOResponseUser> restore(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceUser.restore(id));
    }
}
