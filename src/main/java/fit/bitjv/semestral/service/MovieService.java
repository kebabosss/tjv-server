package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.repository.MovieRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService extends AbstractCrudService<Movie, Long>{
    public MovieService(MovieRepository repository) {
        super(repository);
    }

    //returns average score for this movie. -1 if there´s no review yet
    public double MovieScoreById(Long id)
    {
        Optional<Movie> movie = repository.findById(id);
        if(movie.isEmpty())
            throw new IllegalArgumentException();
        List<Review> reviews = repository.findById(id).get().getReviews();
        if(reviews.isEmpty())
        {
            return -1;
        }
        double result = 0;
        for (Review review : reviews) {
            result += review.getRating();
        }
        return result / reviews.size();
    }
}
