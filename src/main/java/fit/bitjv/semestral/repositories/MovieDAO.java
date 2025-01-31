package fit.bitjv.semestral.repositories;

import fit.bitjv.semestral.domain.Movie;

import java.util.*;

public interface MovieDAO {

    List<Movie> allMovies();

    Movie findMovie(Long MovieId);

    Movie createMovie(Movie ent);

    void deleteMovie(Long id);

}
