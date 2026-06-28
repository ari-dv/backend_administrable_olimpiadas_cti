package togama.demo_backend_olimpiadas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import togama.demo_backend_olimpiadas.model.Grupos;
import togama.demo_backend_olimpiadas.service.GruposService;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/grupos")
@Tag(name = "Módulo de Grupos", description = "API para la gestión de las secciones o grupos asignados a los cursos de las Olimpiadas Escolares")
public class GruposController {

    private final GruposService service;

    public GruposController(GruposService service) {
        this.service = service;
    }

    @Operation(
        summary = "Listar todos los grupos", 
        description = "Obtiene la lista completa de grupos registrados en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista de grupos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Grupos>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(
        summary = "Obtener grupo por ID", 
        description = "Busca los detalles de un grupo específico mediante su identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Grupo encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Grupos> buscarPorId(
            @Parameter(description = "ID único del grupo", example = "1") 
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Registrar un grupo", 
        description = "Crea una nueva sección o grupo y lo asocia en la base de datos."
    )
    @ApiResponse(responseCode = "200", description = "Grupo creado exitosamente")
    @PostMapping
    public ResponseEntity<Grupos> crear(@RequestBody Grupos grupo) {
        return ResponseEntity.ok(service.guardar(grupo));
    }

    @Operation(
        summary = "Actualizar grupo", 
        description = "Modifica la capacidad, nombre u otra información de un grupo existente."
    )
    @ApiResponse(responseCode = "200", description = "Grupo actualizado correctamente")
    @PutMapping("/{id}")
    public ResponseEntity<Grupos> actualizar(
            @Parameter(description = "ID del grupo a modificar", example = "1") 
            @PathVariable Long id, 
            @RequestBody Grupos grupo) {
        grupo.setId(id);
        return ResponseEntity.ok(service.guardar(grupo));
    }

    @Operation(
        summary = "Eliminar grupo", 
        description = "Elimina permanentemente un grupo del sistema."
    )
    @ApiResponse(responseCode = "204", description = "Grupo eliminado correctamente (Sin contenido)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del grupo a eliminar", example = "1") 
            @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Listar grupos por Curso", 
        description = "Devuelve todos los grupos que pertenecen a un curso específico. Fundamental para cargar listas desplegables dependientes (Selects) en el formulario de inscripción del frontend."
    )
    @ApiResponse(responseCode = "200", description = "Grupos filtrados por curso obtenidos exitosamente")
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Grupos>> buscarPorCurso(
            @Parameter(description = "ID del curso padre para filtrar los grupos", example = "2") 
            @PathVariable Long cursoId) {
        return ResponseEntity.ok(service.buscarPorCurso(cursoId));
    }
}