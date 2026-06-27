package togama.demo_backend_olimpiadas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import togama.demo_backend_olimpiadas.model.Cursos;
import togama.demo_backend_olimpiadas.repository.CursosRepository;

@Service
public class CursosService {

    private final CursosRepository repo;

    public CursosService(CursosRepository repo) {
        this.repo = repo;
    }

    public Cursos crear(Cursos cursoDto) {
        Cursos nuevoCurso = new Cursos();
        
        nuevoCurso.setTitle(cursoDto.getTitle());
        nuevoCurso.setLevel(cursoDto.getLevel());
        nuevoCurso.setPrice(cursoDto.getPrice());
        nuevoCurso.setSlug(cursoDto.getTitle().toLowerCase().replace(" ", "-"));
        nuevoCurso.setIsActive(true);
        nuevoCurso.setPeriod(cursoDto.getPeriod());
        nuevoCurso.setImagePath(cursoDto.getImagePath()); 
    
        return repo.save(nuevoCurso);
    }

    public Page<Cursos> listarPaginado(Pageable pageable) {
        return repo.findAll(pageable);
    }


    public Cursos buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    public Cursos actualizar(Long id, Cursos cursoDto) {
 
        Cursos cursoExistente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        cursoExistente.setTitle(cursoDto.getTitle());
        cursoExistente.setLevel(cursoDto.getLevel());
        cursoExistente.setPrice(cursoDto.getPrice());
        cursoExistente.setSlug(cursoDto.getTitle().toLowerCase().replace(" ", "-"));
        cursoExistente.setImagePath(cursoDto.getImagePath());
        cursoExistente.setPeriod(cursoDto.getPeriod());
        if (cursoDto.getIsActive() != null) {
            cursoExistente.setIsActive(cursoDto.getIsActive());
        }
    
        // 3. Guardas los cambios en la base de datos
        return repo.save(cursoExistente);
    }

    public void eliminar(Long id) {
        Cursos curso = buscarPorId(id);
        repo.delete(curso);
    }
}