package fit.bitjv.semestral.rest.dto;

import fit.bitjv.semestral.domain.Movie;
import jakarta.persistence.ManyToOne;

public class ReviewDTO {
    String reviewText;
    int rating;
    Long movieId;

    public ReviewDTO() {
    }

    public ReviewDTO(String reviewText, int rating, Long movieId) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.movieId = movieId;
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
