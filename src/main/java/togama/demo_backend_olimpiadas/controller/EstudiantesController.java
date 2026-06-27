package togama.demo_backend_olimpiadas.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import togama.demo_backend_olimpiadas.model.Estudiantes;
import togama.demo_backend_olimpiadas.repository.EstudiantesRepository;
import togama.demo_backend_olimpiadas.service.EstudiantesService;

@CrossOrigin("*") 
@RestController
@RequestMapping("/api/estudiantes")
public class EstudiantesController {

    private final EstudiantesService service;
    private final EstudiantesRepository estudiantesRepository;

    // 1. CORRECCIÓN: Se agregó estudiantesRepository al constructor
    public EstudiantesController(EstudiantesService service, EstudiantesRepository estudiantesRepository) {
        this.service = service;
        this.estudiantesRepository = estudiantesRepository;
    }

    @PostMapping
    public ResponseEntity<Estudiantes> crear(@RequestBody Estudiantes estudiante) {
        return ResponseEntity.ok(service.crear(estudiante));
    }

    // 2. Endpoint normal para listar (ej. /api/estudiantes?page=0)
    @GetMapping
    public ResponseEntity<Page<Estudiantes>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listarPaginado(pageable));
    }

    // 3. CORRECCIÓN: Se agregó params = "dni" para que no choque con el listar()
    // Esto se activa SOLO si la URL tiene ?dni=...
    @GetMapping(params = "dni")
    public ResponseEntity<?> buscarPorDniParam(@RequestParam String dni) {
        Optional<Estudiantes> estudiante = estudiantesRepository.findByDni(dni);
        if (estudiante.isPresent()) {
            return ResponseEntity.ok(estudiante.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 limpio para que React lo maneje
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiantes> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiantes> actualizar(@PathVariable Long id, @RequestBody Estudiantes estudiante) {
        return ResponseEntity.ok(service.actualizar(id, estudiante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}