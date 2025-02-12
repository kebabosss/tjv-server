package fit.bitjv.semestral.rest;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.rest.dto.DirectorDTO;
import fit.bitjv.semestral.rest.dto.DirectorMapper;
import fit.bitjv.semestral.rest.dto.MovieDTO;
import fit.bitjv.semestral.service.DirectorServices;
import fit.bitjv.semestral.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Get all directors", description = "Returns all directors")
    @GetMapping
    List<DirectorDTO> ReadAll() {
        return directorServices.ReadAll().stream()
                .map(directorMapper::toDTO)
                .toList();
    }

    // @Parameter(name = "id", description = "Director id", example = "1")
    @Operation(summary = "Get directors by id", description = "Returns director found by given id")
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
    @Operation(summary = "Get directors that directed movie", description = "Returns all directors that directed movie found by given id")
    @GetMapping("/movie/{id}")
    List<DirectorDTO> ReadByMovieID(@PathVariable Long id){
       return directorServices.findAllByMovieId(id).stream()
                .map(directorMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Get directors by name and year born", description = "Returns all directors that were found by given name and yearBorn combination")
    @GetMapping("/director")
    List<DirectorDTO> ReadByNameAndYear(@RequestParam String name,@RequestParam int year){
        return directorServices.findAllByNameAndYear(name, year).stream()
                .map(directorMapper::toDTO)
                .toList();
    }


    @Operation(summary = "Creates new director", description = "Returns newly created director")
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

    @Operation(summary = "Updates director", description = "Returns updated director found by given id")
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
            if(e.getMessage().equals("Director not found")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }
    }

    @Operation(summary = "Delete director", description = "Deletes director found by given id")
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
