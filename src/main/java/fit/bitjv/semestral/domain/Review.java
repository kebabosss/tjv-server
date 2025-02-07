package fit.bitjv.semestral.domain;

import jakarta.persistence.*;



@Entity
/*@NamedQuery(name = "reviewsForMovieId", query =
        "SELECT r FROM Review r JOIN r.movie c WHERE c.id = :movieId")*/
//@NamedQuery(name = "allReviews", query = "select review from Review review")
public class Review implements EntityWithID<Long> {
    @Id
    @GeneratedValue
    Long id;
    String reviewText;
    int rating;

    @ManyToOne(optional = false)
    Movie movie;

    public Review(ReviewDTO dto)
    {
        reviewText = dto.reviewText();
        rating = dto.rating();
    }

    public Review(Long id, String reviewText, int rating) {
        this.id = id;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public Review(){}

    public Review(Long id, String reviewText, int rating, Movie movie) {
        this.id = id;
        this.reviewText = reviewText;
        this.rating = rating;
        this.movie = movie;
    }



    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ReviewDTO toDTO()
    {
        return new ReviewDTO(id, reviewText, rating, movie.getId());
    }
    public record ReviewDTO( Long id, String reviewText, int rating, Long movieId){}

}
