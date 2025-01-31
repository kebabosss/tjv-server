package fit.bitjv.semestral.rest;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.repositories.MovieDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("rest/movie")
public class MovieRes {
    @Autowired
    MovieDAO movieDAO;

    @GetMapping
    List<Movie.MovieDTO> all() {
        return movieDAO.allMovies().stream().map(Movie::toDTO).toList();
    }

    @Autowired
    HttpServletRequest httpServletRequest;

    @PostMapping
    public ResponseEntity create(@RequestBody Movie.MovieDTO movieDTO) {
        Long id = movieDAO.createMovie(new Movie(movieDTO));
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/"+ id)).build();
    }

}