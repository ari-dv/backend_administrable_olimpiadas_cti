package togama.demo_backend_olimpiadas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import togama.demo_backend_olimpiadas.model.Estudiantes;

public interface EstudiantesRepository extends JpaRepository<Estudiantes, Long> {
    Optional<Estudiantes> findByDni(String dni);
}