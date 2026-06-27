package togama.demo_backend_olimpiadas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import togama.demo_backend_olimpiadas.model.Estudiantes;
import togama.demo_backend_olimpiadas.model.Grupos;
import togama.demo_backend_olimpiadas.model.Inscripciones;
import togama.demo_backend_olimpiadas.repository.EstudiantesRepository;
import togama.demo_backend_olimpiadas.repository.GruposRepository;
import togama.demo_backend_olimpiadas.repository.InscripcionesRepository;

@Service
public class InscripcionesService {

    private final InscripcionesRepository inscripcionesRepo;
    private final GruposRepository gruposRepo;
    private final EstudiantesRepository estudiantesRepo;

    public InscripcionesService(InscripcionesRepository inscripcionesRepo, GruposRepository gruposRepo, EstudiantesRepository estudiantesRepo) {
        this.inscripcionesRepo = inscripcionesRepo;
        this.gruposRepo = gruposRepo;
        this.estudiantesRepo = estudiantesRepo;
    }

    public Inscripciones inscribirAlumno(Long studentId, Long groupId) {
        
        Estudiantes estudiante = estudiantesRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
                
        Grupos grupo = gruposRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo/Horario no encontrado"));

        if (inscripcionesRepo.existsByEstudianteAndGrupo(estudiante, grupo)) {
            throw new RuntimeException("El estudiante ya está inscrito en este grupo");
        }

        if (grupo.getCapacity() <= 0) {
            throw new RuntimeException("No hay vacantes disponibles en este horario");
        }

        // Restamos vacante al grupo específico
        grupo.setCapacity(grupo.getCapacity() - 1);
        gruposRepo.save(grupo); 

        Inscripciones nuevaInscripcion = new Inscripciones();
        nuevaInscripcion.setEstudiante(estudiante);
        nuevaInscripcion.setGrupo(grupo);
        
        return inscripcionesRepo.save(nuevaInscripcion);
    }

    public Page<Inscripciones> listarPaginado(Pageable pageable) {
        return inscripcionesRepo.findAll(pageable);
    }

    public Inscripciones buscarPorId(Long id) {
        return inscripcionesRepo.findById(id).orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
    }

    public Inscripciones actualizarEstado(Long id, String nuevoEstado, String nuevoEstadoPago) {
        Inscripciones inscripcion = buscarPorId(id);
        if (nuevoEstado != null) inscripcion.setStatus(nuevoEstado);
        if (nuevoEstadoPago != null) inscripcion.setPaymentStatus(nuevoEstadoPago);
        return inscripcionesRepo.save(inscripcion);
    }

    public void cancelarInscripcion(Long id) {
        Inscripciones inscripcion = buscarPorId(id);
        // Devolvemos la vacante al grupo al cancelar
        Grupos grupo = inscripcion.getGrupo();
        grupo.setCapacity(grupo.getCapacity() + 1);
        gruposRepo.save(grupo);
        
        inscripcionesRepo.delete(inscripcion);
    }

    public List<Inscripciones> listarPorGrupo(Long grupoId) {
        return inscripcionesRepo.findByGrupoId(grupoId);
    }

    public List<Inscripciones> listarPorEstudiante(Long estudianteId) {
        return inscripcionesRepo.findByEstudianteId(estudianteId);
    }
}