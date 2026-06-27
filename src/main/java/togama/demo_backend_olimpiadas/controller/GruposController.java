package togama.demo_backend_olimpiadas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import togama.demo_backend_olimpiadas.model.Grupos;
import togama.demo_backend_olimpiadas.service.GruposService;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/grupos")
public class GruposController {

    private final GruposService service;

    public GruposController(GruposService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Grupos>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupos> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Grupos> crear(@RequestBody Grupos grupo) {
        return ResponseEntity.ok(service.guardar(grupo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupos> actualizar(@PathVariable Long id, @RequestBody Grupos grupo) {
        grupo.setId(id);
        return ResponseEntity.ok(service.guardar(grupo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Grupos>> buscarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(service.buscarPorCurso(cursoId));
    }
}