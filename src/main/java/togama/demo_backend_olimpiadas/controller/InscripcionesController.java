package togama.demo_backend_olimpiadas.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import togama.demo_backend_olimpiadas.model.Inscripciones;
import togama.demo_backend_olimpiadas.service.InscripcionesService;

@CrossOrigin("*") 
@RestController
@RequestMapping("/api/inscripciones")
@Tag(name = "Módulo de Inscripciones", description = "API principal para gestionar la matrícula y vinculación de los estudiantes becados con los cursos (grupos)")
public class InscripcionesController {

    private final InscripcionesService service;

    public InscripcionesController(InscripcionesService service) {
        this.service = service;
    }

    @Schema(description = "Estructura de datos enviada desde el frontend para matricular a un alumno")
    public record InscripcionRequest(
        @Schema(description = "ID interno del estudiante becado", example = "10") Long studentId, 
        @Schema(description = "ID del grupo o sección al que se inscribe", example = "5") Long groupId) {}
    
    @Schema(description = "Estructura de datos para actualizar el estado administrativo de la matrícula")
    public record UpdateStatusRequest(
        @Schema(description = "Estado académico actual", example = "ACTIVO") String status, 
        @Schema(description = "Estado del beneficio o pago", example = "BECA_COMPLETA") String paymentStatus) {}

    @Operation(
        summary = "Registrar nueva matrícula", 
        description = "Inscribe a un estudiante en un grupo específico. Este es el endpoint clave que consume el formulario de la web pública (React) al finalizar el registro."
    )
    @ApiResponse(responseCode = "200", description = "Inscripción procesada y guardada con éxito")
    @PostMapping("/inscribir")
    public ResponseEntity<Inscripciones> inscribir(@RequestBody InscripcionRequest request) {
        return ResponseEntity.ok(service.inscribirAlumno(request.studentId(), request.groupId()));
    }

    @Operation(
        summary = "Listar historial de inscripciones", 
        description = "Obtiene el registro completo de matrículas paginadas, diseñado para alimentar la tabla de datos del panel administrativo (Angular)."
    )
    @GetMapping
    public ResponseEntity<Page<Inscripciones>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listarPaginado(pageable));
    }

    @Operation(summary = "Obtener detalle de matrícula", description = "Busca los datos exactos de un trámite de inscripción por su identificador.")
    @GetMapping("/{id}")
    public ResponseEntity<Inscripciones> buscarPorId(
            @Parameter(description = "ID de la inscripción", example = "150") @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Actualizar estados administrativos", 
        description = "Permite a los administradores del CTI cambiar el estado académico o de cobertura de una matrícula existente."
    )
    @PutMapping("/{id}/estado")
    public ResponseEntity<Inscripciones> actualizarEstado(
            @Parameter(description = "ID de la matrícula a modificar", example = "150") @PathVariable Long id, 
            @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(service.actualizarEstado(id, request.status(), request.paymentStatus()));
    }

    @Operation(summary = "Anular inscripción", description = "Cancela o elimina el registro de un estudiante en un curso.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelarInscripcion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Listar matriculados por Grupo", 
        description = "Devuelve todos los estudiantes registrados en una sección en particular. Ideal para generar los padrones y listas de asistencia de los docentes."
    )
    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<Inscripciones>> listarPorGrupo(
            @Parameter(description = "ID de la sección o grupo", example = "5") @PathVariable Long grupoId) {
        return ResponseEntity.ok(service.listarPorGrupo(grupoId));
    }

    @Operation(
        summary = "Listar matrículas por Estudiante", 
        description = "Devuelve todos los cursos en los que un estudiante específico ha asegurado su vacante. Útil para mostrar el historial en el perfil del alumno."
    )
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Inscripciones>> listarPorEstudiante(
            @Parameter(description = "ID del estudiante consultado", example = "10") @PathVariable Long estudianteId) {
        return ResponseEntity.ok(service.listarPorEstudiante(estudianteId));
    }
}