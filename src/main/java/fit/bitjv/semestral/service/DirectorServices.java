package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.repository.DirectorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectorServices extends AbstractCrudService<Director,Long> {
    public DirectorServices(DirectorRepository repository) {
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
}
