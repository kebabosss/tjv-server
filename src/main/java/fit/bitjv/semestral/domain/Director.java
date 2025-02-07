package fit.bitjv.semestral.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Director implements EntityWithID<Long>{
    @Id
    @GeneratedValue
    Long id;
    @ManyToMany
    private List<Movie> moviesDirected = new ArrayList<>();

    String name;
    int yearOfBirth;

    public Director() {
    }

    public Director(Long id, String name, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public List<Movie> getMoviesDirected() {
        return moviesDirected;
    }

    public void setMoviesDirected(List<Movie> moviesDirected) {
        this.moviesDirected = moviesDirected;
    }

    @Override
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

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void removeMovie(Movie movie) {
        moviesDirected.remove(movie);
       // movie.getDirectors().remove(this);
    }

}
