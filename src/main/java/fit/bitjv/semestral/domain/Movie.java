package fit.bitjv.semestral.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQuery(name = "allMovies", query = "select movie from Movie movie")

public class Movie {
    @Id
    @GeneratedValue
    Long id;
    public Movie() {}

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    List<Review> reviews;


    String name;
    int releaseYear;

    public Movie(String name, int releaseYear) {
        this.name = name;
        this.releaseYear = releaseYear;
    }


    public record MovieDTO( Long id, String name, int year) {}
    public Movie(MovieDTO movieDTO) {
        this.name = movieDTO.name;
        this.releaseYear = movieDTO.year;
    }
    public MovieDTO toDTO() { return new MovieDTO(id, name, releaseYear); }

    public String toString() { return toDTO().toString();}

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
