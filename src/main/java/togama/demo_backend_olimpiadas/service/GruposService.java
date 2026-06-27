package togama.demo_backend_olimpiadas.service;

import org.springframework.stereotype.Service;
import togama.demo_backend_olimpiadas.model.Grupos;
import togama.demo_backend_olimpiadas.repository.GruposRepository;
import java.util.List;

@Service
public class GruposService {

    private final GruposRepository gruposRepo;

    public GruposService(GruposRepository gruposRepo) {
        this.gruposRepo = gruposRepo;
    }

    public List<Grupos> listarTodos() {
        return gruposRepo.findAll();
    }

    public Grupos buscarPorId(Long id) {
        return gruposRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
    }

    public Grupos guardar(Grupos grupo) {
        return gruposRepo.save(grupo);
    }

    public void eliminar(Long id) {
        Grupos grupo = buscarPorId(id);
        gruposRepo.delete(grupo);
    }

    public List<Grupos> buscarPorCurso(Long cursoId) {
        return gruposRepo.findByCursoId(cursoId);
    }
}