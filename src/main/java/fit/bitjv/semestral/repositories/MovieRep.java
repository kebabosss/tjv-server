package fit.bitjv.semestral.repositories;

import fit.bitjv.semestral.domain.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRep extends CrudRepository<Movie, Long> {

}
