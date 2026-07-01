package togama.demo_backend_olimpiadas.service;

import org.springframework.stereotype.Service;
import togama.demo_backend_olimpiadas.model.Colegios;
import togama.demo_backend_olimpiadas.repository.ColegiosRepository;
import java.util.List;

@Service
public class ColegiosService {

    private final ColegiosRepository repo;

    public ColegiosService(ColegiosRepository repo) {
        this.repo = repo;
    }

    public List<Colegios> listarTodos() {
        return repo.findAll();
    }

    public Colegios buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Colegio no encontrado"));
    }

    public Colegios guardar(Colegios colegio) {
        return repo.save(colegio);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}