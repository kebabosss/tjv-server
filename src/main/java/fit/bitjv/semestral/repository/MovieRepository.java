package fit.bitjv.semestral.repository;

import fit.bitjv.semestral.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT COUNT(DISTINCT m) FROM Movie m JOIN m.reviews r WHERE r.rating >= 4")
    int countGoodMovies();

    List<Movie> findMovieByNameAndReleaseYear(String name, int year);

    List<Movie> findMovieByDirectors_Id(Long id);
}
