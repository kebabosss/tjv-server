package fit.bitjv.semestral.repositories;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;

import java.util.*;

public interface MovieDAO {

    List<Movie> allMovies();

    Movie findMovie(Long movieId);

    Long createMovie(Movie ent);

    void deleteMovie(Long id);

    List<Review> allReviews();

    Long createReview(Review ent);

    List<Review> reviewsForMovieId(Long movieId);

    void deleteReview(Long id);


}
