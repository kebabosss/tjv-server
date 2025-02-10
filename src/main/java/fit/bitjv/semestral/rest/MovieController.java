package fit.bitjv.semestral.rest;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.rest.dto.DirectorDTO;
import fit.bitjv.semestral.rest.dto.MovieDTO;
import fit.bitjv.semestral.rest.dto.MovieMapper;
import fit.bitjv.semestral.service.DirectorServices;
import fit.bitjv.semestral.service.MovieService;
import fit.bitjv.semestral.service.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("rest/movie")
public class MovieController {

    MovieService movieService;
    DirectorServices directorServices;
    ReviewService reviewService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService, DirectorServices directorServices, ReviewService reviewService) {
        this.movieService = movieService;
        this.directorServices = directorServices;
        this.reviewService = reviewService;
        movieMapper = new MovieMapper(directorServices, reviewService);
    }

    @GetMapping
    List<MovieDTO> ReadAll() {
        return movieService.ReadAll().stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    MovieDTO ReadByID(@PathVariable Long id){
        try {
            return movieMapper.toDTO(movieService.ReadById(id));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/movie")
    List<MovieDTO> ReadByNameAndYear(@RequestParam String name, @RequestParam int year){
        return movieService.findAllByNameAndYear(name, year).stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @GetMapping("/director/{Id}")
    List<MovieDTO> ReadByDirectorId(@PathVariable Long Id)
    {
        return movieService.findAllByDirectorId(Id).stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @GetMapping("/countGood")
    int countGoodMovies() {
        return movieService.countGoodMovie();
    }

    @PostMapping
    public MovieDTO Create(@RequestBody MovieDTO movie) {
        try {
            movie.setId(null);
            return movieMapper.toDTO(movieService.Create(movieMapper.toEntity(movie)));
        } catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public MovieDTO Update(@RequestBody MovieDTO movieDTO, @PathVariable Long id)
    {
        try{
            Movie newMovie = movieMapper.toEntity(movieDTO);
            newMovie.setId(id);
            return movieMapper.toDTO(movieService.Update(newMovie));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void Delete(@PathVariable Long id)
    {
        try {
            movieService.DeleteById(id);
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}