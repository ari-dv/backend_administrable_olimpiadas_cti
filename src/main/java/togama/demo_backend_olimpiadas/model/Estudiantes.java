package togama.demo_backend_olimpiadas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "estudiantes")
@SQLDelete(sql = "UPDATE estudiantes SET is_active = false WHERE id=?") 
@SQLRestriction("is_active = true")
@Getter
@Setter
public class Estudiantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El DNI es obligatorio")
    @Column(unique = true, nullable = false, length = 8)
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String names;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(name = "last_names", nullable = false, length = 100)
    private String lastNames;

    @Column(length = 15)
    private String phone;

    @Column(unique = true, length = 100)
    private String email;

    @Column(name = "image_path", columnDefinition = "LONGTEXT")
    private String imagePath;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "type_scholarship", length = 50)
    private String typeScholarship;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}