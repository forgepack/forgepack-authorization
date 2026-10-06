package dev.forgepack.authorization.internal.payload;

import dev.forgepack.core.api.payload.DTOIdentifiable;
import dev.forgepack.authorization.internal.model.Privilege;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for privilege-related responses.
 *
 * <p>This class represents the data returned to clients for {@link Privilege}
 * entities. It extends {@link org.springframework.hateoas.RepresentationModel}
 * to support Hypermedia as the Engine of Application State (HATEOAS),
 * enabling the inclusion of navigational links in API responses.</p>
 *
 * <h3>Core Attributes</h3>
 * <ul>
 *     <li><b>id</b>: unique identifier of the privilege</li>
 *     <li><b>name</b>: descriptive name of the privilege</li>
 * </ul>
 *
 * <h3>Usage Context</h3>
 * <ul>
 *     <li>Returned by REST endpoints exposing privilege data</li>
 *     <li>Used in role composition and authorization visualization</li>
 *     <li>Can be embedded within other DTOs (e.g., {@link RoleResponse})</li>
 * </ul>
 *
 * <h3>HATEOAS Support</h3>
 * <ul>
 *     <li>Supports adding hypermedia links via {@link Link}</li>
 *     <li>Enables discoverability of related resources and actions</li>
 * </ul>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>This DTO is intended for output only and does not contain validation or persistence logic</li>
 *     <li>Should be constructed via assembler or mapper layer</li>
 *     <li>Promotes decoupling between internal domain models and external API contracts</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see Privilege
 * @see RoleResponse
 * @see org.springframework.hateoas.RepresentationModel
 */
public class PrivilegeResponse extends RepresentationModel<PrivilegeResponse> implements DTOIdentifiable<UUID> {

    private final UUID id;
    private final String name;

    public PrivilegeResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    @Override
    public UUID id() {
        return id;
    }
}
