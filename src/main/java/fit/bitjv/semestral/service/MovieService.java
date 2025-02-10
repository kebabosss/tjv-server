package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.repository.DirectorRepository;
import fit.bitjv.semestral.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService extends AbstractCrudService<Movie, Long>{
    DirectorServices directorServices;

    @Autowired
    public MovieService(JpaRepository<Movie, Long> repository, @Lazy DirectorServices directorServices) {
        super(repository);
        this.directorServices = directorServices;
    }

    public int countGoodMovie()
    {
        return ((MovieRepository)repository).countGoodMovies();
    }
    public List<Movie> findAllByNameAndYear(String name, int year)
    {
        return ((MovieRepository)repository).findMovieByNameAndReleaseYear(name, year);
    }

    public List<Movie> findAllByDirectorId(Long id)
    {
        return ((MovieRepository)repository).findMovieByDirectors_Id(id);
    }

    @Transactional
    @Override
    public void DeleteById(Long Id) {
        Movie movie = repository.findById(Id)
                .orElseThrow(IllegalArgumentException::new);

        for (Director director : movie.getDirectors()) {
            director.removeMovie(movie);
            directorServices.Update(director);
        }
        repository.delete(movie);
    }

    @Override
    public Movie Create(Movie movie) {

        for (Director d : movie.getDirectors())
        {
            d.addMovie(movie);
        }
        Movie savedMovie = repository.save(movie);
        return savedMovie;
    }

    @Transactional
    @Override
    public Movie Update(Movie movie) {
        Movie existingMovie = repository.findById(movie.getId()).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        existingMovie.setName(movie.getName());
        existingMovie.setReleaseYear(movie.getReleaseYear());

        for (Director d : new ArrayList<>(existingMovie.getDirectors())) {
            d.getMoviesDirected().remove(existingMovie);
            existingMovie.getDirectors().remove(d);
        }
        repository.save(existingMovie);

        for (Director d : movie.getDirectors()) {
            d.getMoviesDirected().add(existingMovie);
            existingMovie.getDirectors().add(d);
        }
        return repository.save(existingMovie);
    }
}
