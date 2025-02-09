package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService extends AbstractCrudService<Movie, Long>{
    DirectorServices directorServices;

    public MovieService(JpaRepository<Movie, Long> repository, DirectorServices directorServices) {
        super(repository);
        this.directorServices = directorServices;
    }

    public int countGoodMovie()
    {
        return ((MovieRepository)repository).countGoodMovies();
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

    @Transactional
    @Override
    public Movie Update(Movie movie) {
        var newDirs = movie.getDirectors();
        var oldDirs = directorServices.findAllByMovieId(movie.getId());
        for(Director dir : oldDirs)
        {
            if(!newDirs.contains(dir))
            {
                dir.removeMovie(movie);
                directorServices.Update(dir);
            }
        }
        for(Director dir : newDirs)
        {
            if(!oldDirs.contains(dir))
            {
                dir.addMovie(movie);
                directorServices.Update(dir);
            }
        }
        repository.save(movie);
        return movie;
    }
}
