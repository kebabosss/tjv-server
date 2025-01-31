package fit.bitjv.semestral.rest;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.repositories.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rest/movie")
public class MovieRes {
    @Autowired
    MovieDAO movieDAO;

    @GetMapping
    List<Movie> all() {
        return movieDAO.allMovies();
    }


}