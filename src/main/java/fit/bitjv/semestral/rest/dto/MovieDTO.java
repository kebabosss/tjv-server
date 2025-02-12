package fit.bitjv.semestral.rest.dto;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Review;

import java.util.List;

public class MovieDTO {
    Long id;
    String name;
    int releaseYear;

    List<Long> directors;
    List<Long> reviews;

    public List<Long> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Long> directors) {
        this.directors = directors;
    }

    public List<Long> getReviews() {
        return reviews;
    }

    public void setReviews(List<Long> reviews) {
        this.reviews = reviews;
    }

    public MovieDTO(Long id, String name, int releaseYear, List<Long> directors, List<Long> reviews) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.directors = directors;
        this.reviews = reviews;
    }

    public MovieDTO() {
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

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
