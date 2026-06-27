package togama.demo_backend_olimpiadas.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import togama.demo_backend_olimpiadas.model.Cursos;
import togama.demo_backend_olimpiadas.service.CursosService;

@CrossOrigin("*") 
@RestController
@RequestMapping("/api/cursos")
public class CursosController {

    private final CursosService service;

    public CursosController(CursosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Cursos> crear(@Valid @RequestBody Cursos curso) {
        return ResponseEntity.ok(service.crear(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cursos> actualizar(@PathVariable Long id, @Valid @RequestBody Cursos curso) {
        return ResponseEntity.ok(service.actualizar(id, curso));
    }

    @GetMapping
    public ResponseEntity<Page<Cursos>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listarPaginado(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cursos> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}