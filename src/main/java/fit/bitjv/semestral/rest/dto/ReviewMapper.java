package fit.bitjv.semestral.rest.dto;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.service.MovieService;

public class ReviewMapper {
    private final MovieService movieService;

    public ReviewMapper(MovieService movieService) {
        this.movieService = movieService;
    }

    public Review toEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getReviewId());
        review.setReviewText(reviewDTO.getReviewText());
        review.setRating(reviewDTO.getRating());
        Movie movie = movieService.ReadById(reviewDTO.getMovieId());
        review.setMovie(movie);

        return review;
    }

    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.reviewId = review.getId();
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setMovieId(review.getMovie().getId());

        return reviewDTO;
    }
}
