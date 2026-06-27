package togama.demo_backend_olimpiadas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togama.demo_backend_olimpiadas.model.Cursos;

public interface CursosRepository extends JpaRepository<Cursos, Long> {
}