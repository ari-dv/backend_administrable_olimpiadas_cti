package togama.demo_backend_olimpiadas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import togama.demo_backend_olimpiadas.model.Grupos;

@Repository
public interface GruposRepository extends JpaRepository<Grupos, Long> {
    List<Grupos> findByCursoId(Long cursoId);
}