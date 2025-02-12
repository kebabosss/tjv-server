package fit.bitjv.semestral.rest;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.rest.dto.DirectorDTO;
import fit.bitjv.semestral.rest.dto.MovieDTO;
import fit.bitjv.semestral.rest.dto.MovieMapper;
import fit.bitjv.semestral.service.DirectorServices;
import fit.bitjv.semestral.service.MovieService;
import fit.bitjv.semestral.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
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
        movieService.RemoveNoDirMovies();
        this.movieService = movieService;
        this.directorServices = directorServices;
        this.reviewService = reviewService;
        movieMapper = new MovieMapper(directorServices, reviewService);
    }

    @Operation(summary = "Get all movies", description = "Returns all movies")
    @GetMapping
    List<MovieDTO> ReadAll() {
        movieService.RemoveNoDirMovies();
        return movieService.ReadAll().stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Get movie by id", description = "Returns movie found by given id")
    @GetMapping("/{id}")
    MovieDTO ReadByID(@PathVariable Long id){
        movieService.RemoveNoDirMovies();
        try {
            return movieMapper.toDTO(movieService.ReadById(id));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get movies by name and year", description = "Returns all movies found by given name and yearReleased combination")
    @GetMapping("/movie")
    List<MovieDTO> ReadByNameAndYear(@RequestParam String name, @RequestParam int year){
        movieService.RemoveNoDirMovies();
        return movieService.findAllByNameAndYear(name, year).stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Get all movies by director id", description = "Returns all movies directed by director found by given id")
    @GetMapping("/director/{Id}")
    List<MovieDTO> ReadByDirectorId(@PathVariable Long Id)
    {
        movieService.RemoveNoDirMovies();

        return movieService.findAllByDirectorId(Id).stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Count number of good movies", description = "Returns number of movies that have at least one review with rating 4 or higher")
    @GetMapping("/countGood")
    int countGoodMovies() {

        movieService.RemoveNoDirMovies();
        return movieService.countGoodMovie();
    }

    @Operation(summary = "Create new movie", description = "Returns newly created movie")
    @PostMapping
    public MovieDTO Create(@RequestBody MovieDTO movie) {
        movieService.RemoveNoDirMovies();
        try {
            movie.setId(null);
            return movieMapper.toDTO(movieService.Create(movieMapper.toEntity(movie)));
        } catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update movie", description = "Returns updated movie found by given id")
    @PutMapping("/{id}")
    public MovieDTO Update(@RequestBody MovieDTO movieDTO, @PathVariable Long id)
    {
        movieService.RemoveNoDirMovies();
        try{
            Movie newMovie = movieMapper.toEntity(movieDTO);
            newMovie.setId(id);
            return movieMapper.toDTO(movieService.Update(newMovie));
        }
        catch (IllegalArgumentException e)
        {
            if(e.getMessage().equals("Movie not found")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }
    }

    @Operation(summary = "Delete movie", description = "Deletes movie found by given id")
    @DeleteMapping("/{id}")
    public void Delete(@PathVariable Long id)
    {
        movieService.RemoveNoDirMovies();
        try {
            movieService.DeleteById(id);
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}