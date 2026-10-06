package dev.forgepack.authorization.internal.controller;

import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.payload.UserRequest;
import dev.forgepack.authorization.internal.payload.UserResponse;
import dev.forgepack.authorization.internal.service.UserService;
import dev.forgepack.core.internal.controller.RestorableControllerImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class ControllerUser extends RestorableControllerImpl<User, UserRequest, UserResponse> {

    private final UserService userService;

    public ControllerUser(UserService userService) {
        super(User.class, userService);
        this.userService = userService;
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id){
        userService.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<UserResponse> restore(@PathVariable UUID id){
        return ResponseEntity.accepted().body(userService.restore(id));
    }
}
