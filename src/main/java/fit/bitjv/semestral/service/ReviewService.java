package fit.bitjv.semestral.service;

import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.repository.ReviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends AbstractCrudService<Review,Long> {
    public ReviewService(ReviewRepository repository) {
        super(repository);
    }
}
