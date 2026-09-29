package dev.forgepack.authorization.internal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import dev.forgepack.core.api.model.EntityCrud;
import java.util.HashSet;
import java.util.Set;

/**
 * Domain entity representing an authenticable system user.
 *
 * <p>This entity is responsible for persisting core identity, authentication,
 * and authorization data, including credentials, activation state, login attempt
 * tracking, and associations with access roles ({@link Role}).</p>
 *
 * <h3>Persistence Constraints</h3>
 * <ul>
 *     <li><b>username</b>: unique and indexed</li>
 *     <li><b>email</b>: unique and indexed</li>
 * </ul>
 *
 * <p>Uniqueness constraints are enforced at the database level via
 * {@link jakarta.persistence.UniqueConstraint}, ensuring data integrity
 * and preventing race conditions in concurrent environments.</p>
 *
 * <h3>Security Aspects</h3>
 * <ul>
 *     <li><b>password</b>: authentication credential (expected to be securely stored, e.g., hashed)</li>
 *     <li><b>attempt</b>: counter for failed authentication attempts</li>
 *     <li><b>active</b>: indicates whether the account is enabled</li>
 *     <li><b>secret</b>: optional secret key for multi-factor authentication (2FA)</li>
 * </ul>
 *
 * <h3>Relationships</h3>
 * <ul>
 *     <li><b>Many-to-Many</b> association with {@link Role}, representing authorization profiles</li>
 *     <li>Cascade operations limited to {@link jakarta.persistence.CascadeType#PERSIST}</li>
 *     <li><i>Eager</i> fetching strategy, suitable for authentication/authorization contexts</li>
 * </ul>
 *
 * <h3>Auditing</h3>
 * <p>Full auditing is enabled via {@link org.hibernate.envers.Audited},
 * inheriting creation and modification metadata from {@link EntityCrud}.</p>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>Input validation (e.g., {@code @NotBlank}, {@code @Email}) should be handled at the DTO layer</li>
 *     <li>Business rules (e.g., uniqueness checks) should be enforced in the service layer</li>
 *     <li>This entity is focused solely on persistence and structural integrity concerns</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see EntityCrud
 * @see Role
 */
@Entity
@Table(name = "users",
    indexes = {
        @Index(columnList = "username"),
        @Index(columnList = "email")
    },
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
    }
)
public class User extends EntityCrud {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(columnDefinition = "integer default 0")
    private Integer attempt;
    @Column(columnDefinition = "boolean default true")
    private Boolean active;
    private String secret;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> role = new HashSet<>();

    public User() {
    }
    public User(String username, String email, Set<Role> role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
    public User(String username, String email, String password, Integer attempt, Boolean active, String secret, Set<Role> role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.attempt = attempt;
        this.active = active;
        this.secret = secret;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Integer getAttempt() {
        return attempt;
    }
    public Boolean getActive() {
        return active;
    }
    public String getSecret() {
        return secret;
    }
    public Set<Role> getRole() {
        return role;
    }
}
