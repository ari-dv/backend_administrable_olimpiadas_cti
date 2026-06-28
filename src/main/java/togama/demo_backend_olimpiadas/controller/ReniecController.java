package togama.demo_backend_olimpiadas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import togama.demo_backend_olimpiadas.service.ApiCliente;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/reniec")
@Tag(name = "Integración RENIEC", description = "Servicio de intermediación para la consulta y validación de identidad con entidades externas (apis.net.pe)")
public class ReniecController {

    private final ApiCliente apiCliente;

    public ReniecController(ApiCliente apiCliente) {
        this.apiCliente = apiCliente;
    }

    @Operation(
        summary = "Consultar datos por DNI", 
        description = "Consume la API externa de RENIEC para obtener los nombres y apellidos de un ciudadano. Este endpoint permite que el formulario en React se autocomplete, mejorando la experiencia de usuario (UX) y evitando errores tipográficos."
    )
    @ApiResponse(responseCode = "200", description = "Datos del ciudadano obtenidos y formateados correctamente")
    @ApiResponse(responseCode = "500", description = "Fallo de conexión o timeout con el servicio externo")
    @GetMapping("/consultar")
    public ResponseEntity<?> consultarReniec(
            @Parameter(description = "Número de DNI a consultar (8 dígitos)", example = "73345678") 
            @RequestParam String dni) {
        try {
            return ResponseEntity.ok(apiCliente.consumirApi(dni));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Falló la API de RENIEC\"}");
        }
    }
}