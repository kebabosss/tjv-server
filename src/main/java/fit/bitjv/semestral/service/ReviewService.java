package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.repository.ReviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService extends AbstractCrudService<Review,Long> {
    public ReviewService(ReviewRepository repository) {
        super(repository);
    }
    public List<Review> findAllById(List<Long> Ids)
    {
        return repository.findAllById(Ids);
    }

    public List<Review> findAllByMovieId(Long movieId) {return ((ReviewRepository)repository).findReviewByMovieId(movieId);}
}
