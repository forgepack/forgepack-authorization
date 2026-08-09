package dev.forgepack.authorization.internal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Column;
import dev.forgepack.core.internal.model.GenericAuditEntity;

/**
 * Domain entity representing a fine-grained permission within the authorization model.
 *
 * <p>This entity defines the smallest unit of access control, which can be assigned
 * to {@link Role} entities and, transitively, to users. It is a core component of a
 * role-based access control (RBAC) system, enabling flexible and scalable permission management.</p>
 *
 * <h3>Persistence Constraints</h3>
 * <ul>
 *     <li><b>name</b>: unique and indexed identifier of the privilege</li>
 * </ul>
 *
 * <p>Uniqueness is enforced at the database level via
 * {@link jakarta.persistence.UniqueConstraint}, ensuring data integrity and preventing
 * duplication under concurrent operations.</p>
 *
 * <h3>Usage Context</h3>
 * <ul>
 *     <li>Assigned to {@link Role} entities to compose authorization rules</li>
 *     <li>Represents granular actions (e.g., READ_USER, CREATE_ORDER, DELETE_RESOURCE)</li>
 *     <li>Consumed by authorization frameworks (e.g., Spring Security) for access control decisions</li>
 * </ul>
 *
 * <h3>Auditing</h3>
 * <p>Auditing is enabled via {@link org.hibernate.envers.Audited},
 * inheriting lifecycle metadata from {@link GenericAuditEntity}.</p>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>This entity is immutable in most scenarios and should be managed centrally</li>
 *     <li>Validation concerns should be handled at the DTO layer</li>
 *     <li>Business rules (e.g., naming conventions, reserved privileges) should be enforced in the service layer</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see GenericAuditEntity
 * @see Role
 */
@Entity
@Table(indexes = @Index(columnList = "name"), uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Privilege extends GenericAuditEntity {

    @Column(nullable = false, unique = true)
    private String name;

    public Privilege() {
    }
    public Privilege(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
