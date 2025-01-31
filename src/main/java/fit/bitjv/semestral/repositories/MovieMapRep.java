package fit.bitjv.semestral.repositories;

import fit.bitjv.semestral.domain.Movie;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieMapRep implements MovieDAO {
    public MovieMapRep() {
        init();


    }
    @PostConstruct
    void init() {
        Movie m = createMovie(new Movie("Duna", 2021));
    }

    Map<Long, Movie> movies = new HashMap<>();

    @Override
    public List<Movie> allMovies() {
        return new ArrayList<>(movies.values());
    }

    @Override
    public Movie findMovie(Long MovieId) {
        return movies.get(MovieId);
    }

    @Override
    public Movie createMovie(Movie ent) {
        Long id = movies.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
        ent.setId(id);
        movies.put(id, ent);
        return ent;
    }

    @Override
    public void deleteMovie(Long id) {
        movies.remove(id);
    }
}
