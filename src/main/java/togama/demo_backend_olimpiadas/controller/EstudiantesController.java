package togama.demo_backend_olimpiadas.controller;

import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Módulo de Estudiantes", description = "API para la gestión y validación de los alumnos becados de las Olimpiadas Escolares")
public class EstudiantesController {

    private final EstudiantesService service;
    private final EstudiantesRepository estudiantesRepository;

    public EstudiantesController(EstudiantesService service, EstudiantesRepository estudiantesRepository) {
        this.service = service;
        this.estudiantesRepository = estudiantesRepository;
    }

    @Operation(
        summary = "Registrar un estudiante", 
        description = "Crea el perfil de un nuevo estudiante becado en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Estudiante registrado exitosamente")
    @PostMapping
    public ResponseEntity<Estudiantes> crear(@RequestBody Estudiantes estudiante) {
        return ResponseEntity.ok(service.crear(estudiante));
    }

    @Operation(
        summary = "Listar estudiantes paginados", 
        description = "Obtiene una lista de todos los estudiantes registrados con opciones de paginación para el panel administrativo."
    )
    @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida correctamente")
    @GetMapping
    public ResponseEntity<Page<Estudiantes>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listarPaginado(pageable));
    }

    @Operation(
        summary = "Buscar estudiante por DNI", 
        description = "Permite consultar en tiempo real si un alumno existe en el padrón mediante su Documento Nacional de Identidad (DNI). Ideal para la web pública."
    )
    @ApiResponse(responseCode = "200", description = "Estudiante encontrado y validado")
    @ApiResponse(responseCode = "404", description = "El DNI no está registrado en el sistema")
    @GetMapping(params = "dni")
    public ResponseEntity<?> buscarPorDniParam(
            @Parameter(description = "Número de DNI del estudiante (8 dígitos)", example = "71234568") 
            @RequestParam String dni) {
        Optional<Estudiantes> estudiante = estudiantesRepository.findByDni(dni);
        if (estudiante.isPresent()) {
            return ResponseEntity.ok(estudiante.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 limpio para que React lo maneje
        }
    }

    @Operation(
        summary = "Obtener estudiante por ID", 
        description = "Busca la información detallada de un estudiante específico usando su identificador interno de la base de datos."
    )
    @ApiResponse(responseCode = "200", description = "Información del estudiante recuperada")
    @GetMapping("/{id}")
    public ResponseEntity<Estudiantes> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Actualizar datos del estudiante", 
        description = "Modifica la información personal o académica de un estudiante previamente registrado."
    )
    @ApiResponse(responseCode = "200", description = "Datos actualizados exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<Estudiantes> actualizar(@PathVariable Long id, @RequestBody Estudiantes estudiante) {
        return ResponseEntity.ok(service.actualizar(id, estudiante));
    }

    @Operation(
        summary = "Eliminar estudiante", 
        description = "Elimina permanentemente el registro de un estudiante del sistema."
    )
    @ApiResponse(responseCode = "204", description = "Estudiante eliminado correctamente (Sin contenido)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}