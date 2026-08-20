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
import java.util.HashSet;
import java.util.Set;
import dev.forgepack.core.internal.model.EntityCrud;

/**
 * Domain entity representing a role in the authorization model.
 *
 * <p>This entity defines logical groupings of permissions that can be assigned
 * to users, enabling a role-based access control (RBAC) approach. Roles aggregate
 * multiple {@link Privilege} instances to simplify permission management and
 * enforce consistent authorization policies.</p>
 *
 * <h3>Persistence Constraints</h3>
 * <ul>
 *     <li><b>name</b>: unique and indexed identifier of the role</li>
 * </ul>
 *
 * <p>Uniqueness is enforced at the database level via
 * {@link jakarta.persistence.UniqueConstraint}, ensuring integrity and preventing
 * duplication in concurrent environments.</p>
 *
 * <h3>Relationships</h3>
 * <ul>
 *     <li><b>Many-to-Many</b> association with {@link Privilege}, representing granted permissions</li>
 *     <li>Join table: <b>role_privileges</b></li>
 *     <li>Lazy fetching strategy to optimize performance in non-authorization contexts</li>
 *     <li>Cascade operations limited to {@link jakarta.persistence.CascadeType#PERSIST} and {@link jakarta.persistence.CascadeType#MERGE}</li>
 * </ul>
 *
 * <h3>Usage Context</h3>
 * <ul>
 *     <li>Typically associated with {@link User} entities to define access rights</li>
 *     <li>Used by authorization frameworks (e.g., Spring Security) to evaluate access control rules</li>
 * </ul>
 *
 * <h3>Auditing</h3>
 * <p>Auditing is enabled via {@link org.hibernate.envers.Audited},
 * inheriting lifecycle metadata from {@link GenericAuditEntity}.</p>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>Validation annotations (e.g., {@code @NotBlank}) are preferably handled at the DTO layer</li>
 *     <li>This entity focuses on persistence and structural representation of roles</li>
 *     <li>Business rules (e.g., role hierarchy, immutability of system roles) should be enforced at the service layer</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see GenericAuditEntity
 * @see Privilege
 * @see User
 */
@Entity
@Table(indexes = @Index(columnList = "name"), uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Role extends EntityCrud {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY , cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
    @JoinTable(name = "role_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privilege = new HashSet<>();

    public Role() {
    }
    public Role(String name, Set<Privilege> privilege) {
        this.name = name;
        this.privilege = privilege;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrivilege(Set<Privilege> privilege) {
        this.privilege = privilege;
    }

    public String getName() {
        return name;
    }
    public Set<Privilege> getPrivilege() {
        return privilege;
    }
}
