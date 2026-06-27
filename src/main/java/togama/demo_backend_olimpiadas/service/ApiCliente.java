package togama.demo_backend_olimpiadas.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiCliente {
    private final WebClient web = WebClient.create("https://api.apis.net.pe/v2");
    private final String TOKEN = "apis-token-16217.AT5XTgs7ZegVsB2bbdc1WtrGpJ6RMa3w";

    public ApiCliente() {
    }

    public String consumirApi(String numero) {
        String tipo = numero.length() == 8 ? "reniec/dni" : "sunat/ruc";
    
        Mono<String> respuesta = web.get()
            .uri(uriBuilder -> uriBuilder
                .path("/" + tipo)
                .queryParam("numero", numero)
                .build()
            )
            .header("Authorization", "Bearer " + TOKEN)
            .retrieve()
            .bodyToMono(String.class);
    
        return respuesta.block();
    }
}