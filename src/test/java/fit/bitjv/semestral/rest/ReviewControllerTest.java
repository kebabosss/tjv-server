package fit.bitjv.semestral.rest;

import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.rest.dto.ReviewDTO;
import fit.bitjv.semestral.service.ReviewService;
import fit.bitjv.semestral.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;


    @Test
    public void ReviewUpdateWithInvalidIdThrowsNotFound() throws Exception {

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(1L);
        reviewDTO.setMovieId(1L);
        reviewDTO.setRating(5);
        reviewDTO.setReviewText("Great movie!");

        Mockito.when(reviewService.Update(Mockito.any(Review.class)))
                .thenThrow(IllegalArgumentException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/rest/review/"+ reviewDTO.getReviewId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n\"reviewId\": 1,\n\"movieId\": 1,\n\"rating\": 5,\n\"reviewText\": \"Great movie!\"\n}")
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}