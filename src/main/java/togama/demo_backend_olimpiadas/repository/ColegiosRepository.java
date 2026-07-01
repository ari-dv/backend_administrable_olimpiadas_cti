package togama.demo_backend_olimpiadas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togama.demo_backend_olimpiadas.model.Colegios;

public interface ColegiosRepository extends JpaRepository<Colegios, Long> {
}