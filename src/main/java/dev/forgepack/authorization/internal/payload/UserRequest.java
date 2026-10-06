package dev.forgepack.authorization.internal.payload;

import dev.forgepack.validation.api.annotation.Unique;
import dev.forgepack.core.api.payload.DTOIdentifiable;
import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.service.UserService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for user-related requests.
 *
 * <p>This record represents the input data required for user creation and update
 * operations. It encapsulates validation rules and supports uniqueness checks
 * at the application layer.</p>
 *
 * <h3>Validation Rules</h3>
 * <ul>
 *     <li><b>username</b>: required, must not be blank</li>
 *     <li><b>email</b>: required, must be a valid format, maximum length of 50 characters</li>
 *     <li><b>role</b>: optional set of associated roles</li>
 * </ul>
 *
 * <p>Uniqueness for <b>username</b> and <b>email</b> is enforced at the service layer
 * via {@code @Unique}, and must also be guaranteed at the database level.</p>
 *
 * <h3>Usage Context</h3>
 * <ul>
 *     <li>Used as input for REST endpoints handling user creation and updates</li>
 *     <li>Decouples external API contracts from internal domain entities</li>
 *     <li>Works in conjunction with validation frameworks (Bean Validation)</li>
 * </ul>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>Does not contain persistence logic</li>
 *     <li>Should be mapped to {@link User} entities via a mapper layer</li>
 *     <li>Business rules are enforced in the service layer</li>
 * </ul>
 *
 * @param id unique identifier of the user (optional for creation)
 * @param username unique username of the user
 * @param email unique and valid email address
 * @param role set of roles associated with the user
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see DTOIdentifiable
 * @see RoleResponse
 * @see User
 */
@Unique(service = UserService.class, fields = { "username", "email" })
public record UserRequest(

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String username,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}") @Size(max = 50) @Email
    String email,

    Set<RoleResponse> role
) implements DTOIdentifiable<UUID> {}
