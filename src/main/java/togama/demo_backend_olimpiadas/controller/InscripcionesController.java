package togama.demo_backend_olimpiadas.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import togama.demo_backend_olimpiadas.model.Inscripciones;
import togama.demo_backend_olimpiadas.service.InscripcionesService;

@CrossOrigin("*") 
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionesController {

    private final InscripcionesService service;

    public InscripcionesController(InscripcionesService service) {
        this.service = service;
    }

    // Cambiado: Ahora mapea studentId y groupId
    public record InscripcionRequest(Long studentId, Long groupId) {}
    public record UpdateStatusRequest(String status, String paymentStatus) {}

    @PostMapping("/inscribir")
    public ResponseEntity<Inscripciones> inscribir(@RequestBody InscripcionRequest request) {
        return ResponseEntity.ok(service.inscribirAlumno(request.studentId(), request.groupId()));
    }

    @GetMapping
    public ResponseEntity<Page<Inscripciones>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listarPaginado(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripciones> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Inscripciones> actualizarEstado(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(service.actualizarEstado(id, request.status(), request.paymentStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelarInscripcion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<Inscripciones>> listarPorGrupo(@PathVariable Long grupoId) {
        return ResponseEntity.ok(service.listarPorGrupo(grupoId));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Inscripciones>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(service.listarPorEstudiante(estudianteId));
    }
}