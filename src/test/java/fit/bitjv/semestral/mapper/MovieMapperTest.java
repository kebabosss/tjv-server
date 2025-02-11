package fit.bitjv.semestral.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.rest.dto.MovieDTO;
import fit.bitjv.semestral.rest.dto.MovieMapper;
import fit.bitjv.semestral.service.DirectorServices;
import fit.bitjv.semestral.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class MovieMapperTest {

    @Mock
    private DirectorServices directorServices;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setName("Inception");
        movieDTO.setReleaseYear(2010);
        movieDTO.setDirectors(Arrays.asList(100L, 101L));
        movieDTO.setReviews(Arrays.asList(200L, 201L));

        Director director1 = new Director();
        director1.setId(100L);
        Director director2 = new Director();
        director2.setId(101L);

        Review review1 = new Review();
        review1.setId(200L);
        Review review2 = new Review();
        review2.setId(201L);

        when(directorServices.findAllById(Arrays.asList(100L, 101L))).thenReturn(Arrays.asList(director1, director2));
        when(reviewService.findAllById(Arrays.asList(200L, 201L))).thenReturn(Arrays.asList(review1, review2));

        Movie movie = movieMapper.toEntity(movieDTO);

        assertNotNull(movie);
        assertEquals(movieDTO.getId(), movie.getId());
        assertEquals(movieDTO.getName(), movie.getName());
        assertEquals(movieDTO.getReleaseYear(), movie.getReleaseYear());
        assertEquals(2, movie.getDirectors().size());
        assertEquals(2, movie.getReviews().size());
    }

    @Test
    void testToDTO() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Inception");
        movie.setReleaseYear(2010);

        Director director1 = new Director();
        director1.setId(100L);
        Director director2 = new Director();
        director2.setId(101L);
        movie.setDirectors(Arrays.asList(director1, director2));

        Review review1 = new Review();
        review1.setId(200L);
        Review review2 = new Review();
        review2.setId(201L);
        movie.setReviews(Arrays.asList(review1, review2));

        MovieDTO movieDTO = movieMapper.toDTO(movie);

        assertNotNull(movieDTO);
        assertEquals(movie.getId(), movieDTO.getId());
        assertEquals(movie.getName(), movieDTO.getName());
        assertEquals(movie.getReleaseYear(), movieDTO.getReleaseYear());
        assertEquals(2, movieDTO.getDirectors().size());
        assertEquals(2, movieDTO.getReviews().size());
    }

    @Test
    void testToEntityWithEmptyReviews() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(2L);
        movieDTO.setName("Tenet");
        movieDTO.setReleaseYear(2020);
        movieDTO.setDirectors(Collections.singletonList(102L));
        movieDTO.setReviews(null);

        Director director = new Director();
        director.setId(102L);

        when(directorServices.findAllById(Collections.singletonList(102L))).thenReturn(Collections.singletonList(director));

        Movie movie = movieMapper.toEntity(movieDTO);

        assertNotNull(movie);
        assertEquals(2L, movie.getId());
        assertEquals("Tenet", movie.getName());
        assertEquals(2020, movie.getReleaseYear());
        assertEquals(1, movie.getDirectors().size());
        assertNull(movie.getReviews());
    }
}
