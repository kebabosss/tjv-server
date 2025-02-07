package fit.bitjv.semestral.repositoriesOld;

import fit.bitjv.semestral.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRep extends CrudRepository<Movie, Long> {

}
