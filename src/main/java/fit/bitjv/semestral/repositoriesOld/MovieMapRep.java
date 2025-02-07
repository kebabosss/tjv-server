package fit.bitjv.semestral.repositoriesOld;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MovieMapRep implements MovieDAO {
    public MovieMapRep() {
        init();
    }
    @PostConstruct
    void init() {
       Long id = createMovie(new Movie("Duna", 2021));
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
    public Long createMovie(Movie ent) {
        Long id = movies.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
        ent.setId(id);
        movies.put(id, ent);
        return ent.getId();
    }

    @Override
    public void deleteMovie(Long id) {
        movies.remove(id);
    }

    @Override
    public List<Review> allReviews() {
        return null;
    }

    @Override
    public Long createReview(Review ent) {
        return null;
    }

    @Override
    public List<Review> reviewsForMovieId(Long movieId) {
        return null;
    }

    @Override
    public void deleteReview(Long id) {

    }
}
