package togama.demo_backend_olimpiadas.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import togama.demo_backend_olimpiadas.model.Estudiantes;
import togama.demo_backend_olimpiadas.repository.EstudiantesRepository;

@Service
public class EstudiantesService {

    private final EstudiantesRepository repo;

    public EstudiantesService(EstudiantesRepository repo) {
        this.repo = repo;
    }

    public Estudiantes crear(Estudiantes estudiante) {
        return repo.save(estudiante);
    }

    public Page<Estudiantes> listarPaginado(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Estudiantes buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    public Estudiantes actualizar(Long id, Estudiantes datosActualizados) {
        Estudiantes estudiante = buscarPorId(id);
        estudiante.setNames(datosActualizados.getNames());
        estudiante.setLastNames(datosActualizados.getLastNames());
        estudiante.setPhone(datosActualizados.getPhone());
        estudiante.setEmail(datosActualizados.getEmail());
        estudiante.setTypeScholarship(datosActualizados.getTypeScholarship());
        estudiante.setImagePath(datosActualizados.getImagePath()); 
        
        return repo.save(estudiante);
    }
    public void eliminar(Long id) {
        Estudiantes estudiante = buscarPorId(id);
        repo.delete(estudiante);
    }
}