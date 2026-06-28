package togama.demo_backend_olimpiadas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Módulo de Cursos", description = "API para la gestión del catálogo de cursos de las Olimpiadas Escolares")
public class CursosController {

    private final CursosService service;

    public CursosController(CursosService service) {
        this.service = service;
    }

    @Operation(
        summary = "Registrar un nuevo curso", 
        description = "Crea un curso en la base de datos validando que los datos enviados cumplan con las reglas del CTI."
    )
    @ApiResponse(responseCode = "200", description = "Curso creado exitosamente")
    @PostMapping
    public ResponseEntity<Cursos> crear(@Valid @RequestBody Cursos curso) {
        return ResponseEntity.ok(service.crear(curso));
    }

    @Operation(
        summary = "Actualizar curso existente", 
        description = "Modifica la información de un curso previamente registrado utilizando su ID."
    )
    @ApiResponse(responseCode = "200", description = "Curso actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "El ID del curso no existe")
    @PutMapping("/{id}")
    public ResponseEntity<Cursos> actualizar(@PathVariable Long id, @Valid @RequestBody Cursos curso) {
        return ResponseEntity.ok(service.actualizar(id, curso));
    }

    @Operation(
        summary = "Listar cursos paginados", 
        description = "Devuelve una lista de cursos optimizada mediante paginación para no sobrecargar el panel administrativo."
    )
    @ApiResponse(responseCode = "200", description = "Lista de cursos devuelta correctamente")
    @GetMapping
    public ResponseEntity<Page<Cursos>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listarPaginado(pageable));
    }

    @Operation(
        summary = "Obtener curso por ID", 
        description = "Busca y devuelve los detalles de un curso específico mediante su identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Curso encontrado")
    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Cursos> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Eliminar curso", 
        description = "Elimina de forma permanente un curso del sistema a partir de su ID."
    )
    @ApiResponse(responseCode = "204", description = "Curso eliminado correctamente (Sin contenido)")
    @ApiResponse(responseCode = "404", description = "El ID del curso no existe")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}