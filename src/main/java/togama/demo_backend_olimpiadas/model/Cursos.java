package togama.demo_backend_olimpiadas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cursos")
@SQLDelete(sql = "UPDATE cursos SET is_active = false WHERE id=?") 
@SQLRestriction("is_active = true")
@Getter
@Setter
public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "El slug es obligatorio")
    @Column(unique = true, nullable = false)
    private String slug;

    @NotBlank(message = "El nivel es obligatorio")
    @Column(nullable = false, length = 50)
    private String level;

    @Column(name = "badge_class", length = 50)
    private String badgeClass;

    @Column(length = 50)
    private String duration;

    @Column(name = "schedule_info", length = 100)
    private String scheduleInfo;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "period")
    private String period;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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