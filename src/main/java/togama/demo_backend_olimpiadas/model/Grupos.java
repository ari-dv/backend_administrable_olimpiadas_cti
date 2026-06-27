package togama.demo_backend_olimpiadas.model;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grupos")
@Getter
@Setter
public class Grupos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Cursos curso;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String days; 

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; 

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime; 

    @Column(nullable = false)
    private Integer capacity; 

    @Column(name = "is_active")
    private Boolean isActive = true;
}