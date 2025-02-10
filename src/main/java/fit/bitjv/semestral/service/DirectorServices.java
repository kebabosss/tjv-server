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
    MovieService movieService;

    @Autowired
    public DirectorServices(JpaRepository<Director, Long> repository, MovieService movieRepository) {
        super(repository);
        this.movieService = movieRepository;
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

    @Transactional
    @Override
    public Director Update(Director updatedDirector) {
        Director existingDirector = repository.findById(updatedDirector.getId())
                .orElseThrow(() -> new IllegalArgumentException("Director not found"));

        existingDirector.setName(updatedDirector.getName());
        existingDirector.setMoviesDirected(updatedDirector.getMoviesDirected());
        existingDirector.setYearOfBirth(updatedDirector.getYearOfBirth());


        Director savedDirector = repository.save(existingDirector);

        List<Movie> moviesWithoutDirectors = movieService.ReadAll()
                .stream()
                .filter(movie -> movie.getDirectors().isEmpty())
                .toList();  // Použijeme `toList()` pro nový seznam, abychom se vyhnuli ConcurrentModificationException

        for (Movie movie : moviesWithoutDirectors) {
            System.out.println("Deleting movie without director: " + movie.getName());
            movieService.DeleteById(movie.getId());
            movieService.repository.flush();
        }

        return savedDirector;
    }
}
