package togama.demo_backend_olimpiadas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import togama.demo_backend_olimpiadas.model.Estudiantes;
import togama.demo_backend_olimpiadas.model.Grupos;
import togama.demo_backend_olimpiadas.model.Inscripciones;


public interface InscripcionesRepository extends JpaRepository<Inscripciones, Long> {
    boolean existsByEstudianteAndGrupo(Estudiantes estudiante, Grupos grupo);
    List<Inscripciones> findByGrupoId(Long grupoId);

    List<Inscripciones> findByEstudianteId(Long estudianteId);
}