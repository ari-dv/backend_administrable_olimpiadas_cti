package togama.demo_backend_olimpiadas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import togama.demo_backend_olimpiadas.service.ApiCliente;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/reniec")
public class ReniecController {

    private final ApiCliente apiCliente;

    public ReniecController(ApiCliente apiCliente) {
        this.apiCliente = apiCliente;
    }

    @GetMapping("/consultar")
    public ResponseEntity<?> consultarReniec(@RequestParam String dni) {
        try {
            // Devuelve el JSON de apis.net.pe directo a React
            return ResponseEntity.ok(apiCliente.consumirApi(dni));
        } catch (Exception e) {
            // Si la API externa falla, mandamos un error limpio
            return ResponseEntity.status(500).body("{\"error\": \"Falló la API de RENIEC\"}");
        }
    }
}