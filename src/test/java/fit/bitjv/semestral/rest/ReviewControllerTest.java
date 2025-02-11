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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private MovieService movieService;

    @Test
    public void RepeatedReviewCreationResultsInException() throws Exception {

        // Příklad vytvoření reviewDTO objektu
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setMovieId(1L);
        reviewDTO.setRating(5);
        reviewDTO.setReviewText("Great movie!");

        // Mockování chování ReviewService pro již existující recenzi
        Mockito.when(reviewService.Create(Mockito.any(Review.class)))
                .thenThrow(IllegalArgumentException.class);

        // Provádíme POST request na API pro vytvoření recenze
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/rest/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\n\"movieId\": 1,\n\"rating\": 5,\n\"reviewText\": \"Great movie!\"\n}")
        )
        .andExpect(MockMvcResultMatchers.status().isConflict()); // Očekáváme, že bude vrácen status 409
    }
}