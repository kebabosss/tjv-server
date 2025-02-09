package fit.bitjv.semestral.repository;

import fit.bitjv.semestral.domain.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director,Long> {
    List<Director> findAllByNameAndYearOfBirth(String name, int yearBorn);
    List<Director> findAllByMoviesDirected_Id(Long id);
}
