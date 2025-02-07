package fit.bitjv.semestral.rest;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.repositoriesOld.MovieDAO;
import fit.bitjv.semestral.rest.dto.MovieDTO;
import fit.bitjv.semestral.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("rest/movie")
public class MovieController {

    MovieService movieService;
    private final ModelMapper modelMapper;


    public MovieController(MovieService movieService, ModelMapper modelMapper) {
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    List<MovieDTO> ReadAll() {
        return ConvertManyToDTO(movieService.ReadAll());
    }

    @GetMapping("/{id}")
    MovieDTO ReadByID(@PathVariable Long id){
        try {
            return ConvertToDTO(movieService.ReadById(id));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping
    public Movie Create(@RequestBody MovieDTO movie) {
        try {
            return movieService.Create(ConvertToEntity(movie));
        } catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public Movie Update(@RequestBody MovieDTO movie, @PathVariable Long id)
    {
        try{
            Movie oldMovie = movieService.ReadById(id);
            Movie newMovie = ConvertToEntity(movie);
            newMovie.setId(id);
            return movieService.Update(ConvertToEntity(ConvertToDTO(newMovie)));
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

    private MovieDTO ConvertToDTO(Movie movie)
    {
        return modelMapper.map(movie, MovieDTO.class);
    }

    private Movie ConvertToEntity(MovieDTO movieDTO)
    {
        return modelMapper.map(movieDTO, Movie.class);
    }

    private List<MovieDTO> ConvertManyToDTO(List<Movie> movies) {
        return movies.stream()
                .map(this::ConvertToDTO)
                .toList();
    }


}