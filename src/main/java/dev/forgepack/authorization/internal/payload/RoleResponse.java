package dev.forgepack.authorization.internal.payload;

import dev.forgepack.core.api.payload.DTOIdentifiable;
import dev.forgepack.authorization.internal.model.Privilege;
import dev.forgepack.authorization.internal.model.Role;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for role-related responses.
 *
 * <p>This class represents the data returned to clients for {@link Role}
 * entities. It extends {@link org.springframework.hateoas.RepresentationModel}
 * to support Hypermedia as the Engine of Application State (HATEOAS),
 * allowing the inclusion of navigational links in API responses.</p>
 *
 * <h3>Core Attributes</h3>
 * <ul>
 *     <li><b>id</b>: unique identifier of the role</li>
 *     <li><b>name</b>: role name</li>
 *     <li><b>privilege</b>: set of associated privileges defining access rights</li>
 * </ul>
 *
 * <h3>Usage Context</h3>
 * <ul>
 *     <li>Returned by REST endpoints exposing role data</li>
 *     <li>Represents role-permission composition in RBAC systems</li>
 *     <li>Can be embedded within other DTOs (e.g., {@link UserResponse})</li>
 * </ul>
 *
 * <h3>HATEOAS Support</h3>
 * <ul>
 *     <li>Supports hypermedia links via {@link Link}</li>
 *     <li>Enables discoverability of related resources and actions</li>
 * </ul>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>This DTO is intended for output only and does not contain validation or persistence logic</li>
 *     <li>Should be constructed via an assembler or mapper layer</li>
 *     <li>Promotes decoupling between domain entities and external API contracts</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see Role
 * @see Privilege
 * @see PrivilegeResponse
 * @see org.springframework.hateoas.RepresentationModel
 */

public class RoleResponse extends RepresentationModel<RoleResponse> implements DTOIdentifiable<UUID> {

    private final UUID id;
    private final String name;
    private Set<PrivilegeResponse> privilege = new HashSet<>();

    public RoleResponse(UUID id, String name, Set<PrivilegeResponse> privilege) {
        this.id = id;
        this.name = name;
        this.privilege = privilege;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Set<PrivilegeResponse> getDTOResponsePrivilege() {
        return privilege;
    }

    @Override
    public UUID id() {
        return id;
    }
}
