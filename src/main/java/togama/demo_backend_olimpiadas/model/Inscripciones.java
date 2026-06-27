package togama.demo_backend_olimpiadas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "inscripciones")
@SQLDelete(sql = "UPDATE inscripciones SET is_active = false WHERE id=?") 
@SQLRestriction("is_active = true")
@Getter
@Setter
public class Inscripciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Grupos grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Estudiantes estudiante;

    @Column(length = 50)
    private String status = "PENDIENTE";

    @Column(name = "applied_scholarship", length = 50)
    private String appliedScholarship;

    @Column(name = "payment_status", length = 50)
    private String paymentStatus = "PENDIENTE";

    @Column(name = "amount_paid", precision = 8, scale = 2)
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        enrollmentDate = LocalDateTime.now();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}