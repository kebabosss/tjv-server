package fit.bitjv.semestral.rest.dto;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.service.MovieService;

import java.util.ArrayList;
import java.util.List;

public class DirectorMapper {
    private final MovieService movieService;

    public DirectorMapper(MovieService movieService) {
        this.movieService = movieService;
    }

    public Director toEntity(DirectorDTO directorDTO) {
        Director director = new Director();
        director.setId(directorDTO.getId());
        director.setName(directorDTO.getName());
        director.setYearOfBirth(directorDTO.getYearOfBirth());

        if (directorDTO.getMoviesDirected() != null) {
            List<Movie> movies = new ArrayList<>();
            for (Long movieId : directorDTO.getMoviesDirected()) {
                Movie movie = movieService.ReadById(movieId);
                if (movie != null) {
                    movies.add(movie);
                }
            }
            director.setMoviesDirected(movies);
        }

        return director;
    }

    public DirectorDTO toDTO(Director director) {
        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setId(director.getId());
        directorDTO.setName(director.getName());
        directorDTO.setYearOfBirth(director.getYearOfBirth());

        if (director.getMoviesDirected() != null) {
            List<Long> moviesDirected = new ArrayList<>();
            for (Movie movie : director.getMoviesDirected()) {
                moviesDirected.add(movie.getId());
            }
            directorDTO.setMoviesDirected(moviesDirected);
        }

        return directorDTO;
    }
}