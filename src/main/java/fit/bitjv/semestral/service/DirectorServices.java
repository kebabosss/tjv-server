package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.repository.DirectorRepository;
import fit.bitjv.semestral.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServices extends AbstractCrudService<Director,Long> {

    @Autowired
    public DirectorServices(JpaRepository<Director, Long> repository) {
        super(repository);
    }
    public List<Director> findAllById(List<Long> Ids)
    {
       return repository.findAllById(Ids);
    }

    public List<Director> findAllByNameAndYear(String name, int yearBorn)
    {
        return ((DirectorRepository)repository).findAllByNameAndYearOfBirth(name, yearBorn);
    }

    public List<Director> findAllByMovieId(Long movieId)
    {
        return ((DirectorRepository)repository).findAllByMoviesDirected_Id(movieId);
    }

    @Override
    public Director Create(Director entity) {
        List<Director> duplicates = ((DirectorRepository)repository).findAllByNameAndYearOfBirth(entity.getName(), entity.getYearOfBirth());
        if(!duplicates.isEmpty())
        {
            throw new IllegalArgumentException("Director with this name and year already exists");
        }
        return super.Create(entity);
    }

    @Override
    public Director Update(Director entity) {
        Director existingDirector = repository.findById(entity.getId()).orElseThrow(() -> new IllegalArgumentException("Director not found"));
        List<Director> duplicates = ((DirectorRepository)repository).findAllByNameAndYearOfBirth(entity.getName(), entity.getYearOfBirth());
        if(!duplicates.isEmpty())
        {
            if(!duplicates.get(0).getId().equals(entity.getId())) {
                throw new IllegalArgumentException("Director with this name and year already exists");
            }
        }
        return super.Update(entity);
    }
}
