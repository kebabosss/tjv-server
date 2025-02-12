package fit.bitjv.semestral.rest.dto;

import fit.bitjv.semestral.domain.Movie;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

public class DirectorDTO {
    Long id;
    private List<Long> moviesDirected;
    String name;
    int yearOfBirth;

    public DirectorDTO(Long id, List<Long> moviesDirected, String name, int yearOfBirth) {
        this.id = id;
        this.moviesDirected = moviesDirected;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public DirectorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMoviesDirected() {
        return moviesDirected;
    }

    public void setMoviesDirected(List<Long> moviesDirected) {
        this.moviesDirected = moviesDirected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
