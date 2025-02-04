package fit.bitjv.semestral.rest;


import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.repositories.MovieDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("rest/review")
public class ReviewRes {
    @Autowired
    MovieDAO movieDAO;
    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping
    public List<Review.ReviewDTO> allReviews() {
        return movieDAO.allReviews().stream().map(Review::toDTO).toList();
    }


    @PostMapping
    public ResponseEntity create(@RequestBody Review.ReviewDTO reviewDTO) {
        Movie movie = movieDAO.findMovie(reviewDTO.movieId());
        Review review = new Review(reviewDTO);
        review.setMovie(movie);
        Long id = movieDAO.createReview(review);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/" + id)).build();
    }
}
