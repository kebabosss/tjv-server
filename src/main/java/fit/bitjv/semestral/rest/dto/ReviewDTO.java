package fit.bitjv.semestral.rest.dto;

import fit.bitjv.semestral.domain.Movie;
import jakarta.persistence.ManyToOne;

public class ReviewDTO {
    Long reviewId;
    String reviewText;
    int rating;
    Long movieId;

    public ReviewDTO() {
    }

    public ReviewDTO(Long reviewId, String reviewText, int rating, Long movieId) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.movieId = movieId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
