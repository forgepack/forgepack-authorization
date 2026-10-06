package dev.forgepack.authorization.internal.payload;

import dev.forgepack.core.api.payload.DTOIdentifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for authentication token responses.
 *
 * <p>This class represents the authentication payload returned to clients
 * after successful authentication or token refresh operations. It encapsulates
 * both access and refresh tokens, along with the associated authorization context.</p>
 *
 * <p>Extends {@link org.springframework.hateoas.RepresentationModel} to support
 * Hypermedia as the Engine of Application State (HATEOAS), allowing the inclusion
 * of navigational links in API responses.</p>
 *
 * <h3>Core Attributes</h3>
 * <ul>
 *     <li><b>tokenType</b>: type of the token (typically "Bearer")</li>
 *     <li><b>accessToken</b>: short-lived token used to access protected resources</li>
 *     <li><b>refreshToken</b>: long-lived token used to obtain new access tokens</li>
 *     <li><b>role</b>: set of roles granted to the authenticated user</li>
 * </ul>
 *
 * <h3>Usage Context</h3>
 * <ul>
 *     <li>Returned by authentication endpoints (e.g., login, token refresh)</li>
 *     <li>Used by clients to authorize subsequent requests</li>
 *     <li>Supports stateless authentication mechanisms (e.g., JWT-based systems)</li>
 * </ul>
 *
 * <h3>HATEOAS Support</h3>
 * <ul>
 *     <li>Supports hypermedia links via {@link Link}</li>
 *     <li>Enables discoverability of related authentication and authorization actions</li>
 * </ul>
 *
 * <h3>Security Considerations</h3>
 * <ul>
 *     <li>Tokens must be transmitted exclusively over secure channels (HTTPS)</li>
 *     <li>Access tokens should be short-lived to minimize exposure risk</li>
 *     <li>Refresh tokens must be securely stored and protected against leakage</li>
 *     <li>Sensitive values (e.g., tokens) should not be logged or exposed in plain text</li>
 * </ul>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>This DTO is output-only and does not contain persistence logic</li>
 *     <li>Should be constructed by the authentication service or an assembler layer</li>
 *     <li>Encapsulates both authentication result and authorization context</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see org.springframework.hateoas.RepresentationModel
 */
public class UserResponse extends RepresentationModel<UserResponse> implements DTOIdentifiable<UUID> {

    private final UUID id;
    private final String username;
    private final String email;
    private final Integer attempt;
    private final Boolean active;
    private final Set<RoleResponse> role;

    public UserResponse(UUID id, String username, String email, Integer attempt, Boolean active, Set<RoleResponse> role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.attempt = attempt;
        this.active = active;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public Integer getAttempt() {
        return attempt;
    }
    public Boolean getActive() {
        return active;
    }
    public Set<RoleResponse> getRole() {
        return role;
    }

    @Override
    public UUID id() {
        return id;
    }
}
