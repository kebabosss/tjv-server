package fit.bitjv.semestral.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
//@NamedQuery(name = "allMovies", query = "select movie from Movie movie")
public class Movie implements EntityWithID<Long> {
    @Id
    @GeneratedValue
    Long id;
    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    List<Review> reviews;

    @ManyToMany(mappedBy = "moviesDirected")
    private List<Director> directors;

    String name;
    int releaseYear;

    public Movie() {}

    public Movie(String name, int releaseYear) {
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int year) {
        this.releaseYear = year;
    }

}
