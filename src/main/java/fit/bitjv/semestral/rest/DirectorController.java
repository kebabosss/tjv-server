package fit.bitjv.semestral.rest;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.rest.dto.DirectorDTO;
import fit.bitjv.semestral.rest.dto.DirectorMapper;
import fit.bitjv.semestral.rest.dto.MovieDTO;
import fit.bitjv.semestral.service.DirectorServices;
import fit.bitjv.semestral.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("rest/director")
public class DirectorController {
    DirectorServices directorServices;
    MovieService movieService;
    private final DirectorMapper directorMapper;

    public DirectorController(DirectorServices directorServices, MovieService movieService) {
        this.directorServices = directorServices;
        this.movieService = movieService;
        directorMapper = new DirectorMapper(movieService);
    }

    @GetMapping
    List<DirectorDTO> ReadAll() {
        return directorServices.ReadAll().stream()
                .map(directorMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    DirectorDTO ReadByID(@PathVariable Long id){
        try {
            return directorMapper.toDTO(directorServices.ReadById(id));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/movie/{id}")
    List<DirectorDTO> ReadByMovieID(@PathVariable Long id){
       return directorServices.findAllByMovieId(id).stream()
                .map(directorMapper::toDTO)
                .toList();
    }

    @GetMapping("/name={name};year={year}")
    List<DirectorDTO> ReadByNameAndYear(@PathVariable String name,@PathVariable int year){
        return directorServices.findAllByNameAndYear(name, year).stream()
                .map(directorMapper::toDTO)
                .toList();
    }



    @PostMapping
    public DirectorDTO Create(@RequestBody DirectorDTO directorDTO) {
        try {
            directorDTO.setId(null);
            return directorMapper.toDTO(directorServices.Create(directorMapper.toEntity(directorDTO)));
        } catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public DirectorDTO Update(@RequestBody DirectorDTO directorDTO, @PathVariable Long id)
    {
        try{
            Director newDirector = directorMapper.toEntity(directorDTO);
            newDirector.setId(id);
            return directorMapper.toDTO(directorServices.Update(newDirector));
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
            directorServices.DeleteById(id);
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
