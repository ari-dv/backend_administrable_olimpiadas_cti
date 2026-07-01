package togama.demo_backend_olimpiadas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "colegios")
@SQLDelete(sql = "UPDATE colegios SET is_active = false WHERE id=?") 
@SQLRestriction("is_active = true")
@Getter
@Setter
public class Colegios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String type;

    @Column(name = "is_active")
    private Boolean isActive = true;
}