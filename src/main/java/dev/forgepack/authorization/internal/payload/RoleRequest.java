package dev.forgepack.authorization.internal.payload;

import dev.forgepack.validation.api.annotation.Unique;
import dev.forgepack.core.api.payload.DTOIdentifiable;
import dev.forgepack.authorization.internal.model.Role;
import dev.forgepack.authorization.internal.service.RoleService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for role-related requests.
 *
 * <p>This record represents the input data required for creating and updating
 * {@link Role} entities. It encapsulates validation rules and integrates with
 * the service layer to enforce uniqueness constraints.</p>
 *
 * <h3>Validation Rules</h3>
 * <ul>
 *     <li><b>name</b>: required, must not be blank</li>
 *     <li><b>privilege</b>: optional set of associated privileges</li>
 * </ul>
 *
 * <p>Uniqueness of the <b>name</b> field is enforced at the application layer via
 * {@code @Unique}, and must also be guaranteed at the database level.</p>
 *
 * <h3>Usage Context</h3>
 * <ul>
 *     <li>Used as input for REST endpoints handling role creation and updates</li>
 *     <li>Decouples API contracts from internal domain models</li>
 *     <li>Supports role-permission composition in RBAC systems</li>
 * </ul>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>This DTO does not contain persistence logic</li>
 *     <li>Should be mapped to {@link Role} entities via a mapper layer</li>
 *     <li>Business rules (e.g., reserved roles, naming conventions) should be enforced in the service layer</li>
 * </ul>
 *
 * @param id unique identifier of the role (optional for creation)
 * @param name unique role name
 * @param privilege set of privileges associated with the role
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see DTOIdentifiable
 * @see PrivilegeResponse
 * @see Role
 */
@Unique(service = RoleService.class, fields = { "name" })
public record RoleRequest(

        UUID id,
        @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
        String name,
        Set<PrivilegeResponse> privilege
) implements DTOIdentifiable<UUID> {
}
