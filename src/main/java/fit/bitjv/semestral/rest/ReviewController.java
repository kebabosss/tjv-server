package fit.bitjv.semestral.rest;


import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.rest.dto.ReviewDTO;
import fit.bitjv.semestral.rest.dto.ReviewMapper;
import fit.bitjv.semestral.service.MovieService;
import fit.bitjv.semestral.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("rest/review")
public class ReviewController {
    ReviewService reviewService;
    MovieService movieService;
    private final ReviewMapper reviewMapper;


    public ReviewController(ReviewService reviewServiceService, MovieService movieService) {
        this.reviewService = reviewServiceService;
        this.movieService = movieService;
        reviewMapper = new ReviewMapper(movieService);
    }

    @Operation(summary = "Get all reviews", description = "Returns all reviews")
    @GetMapping
    List<ReviewDTO> ReadAll() {
        return reviewService.ReadAll().stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Get review by id", description = "Returns review found by given id")
    @GetMapping("/{id}")
    ReviewDTO ReadByID(@PathVariable Long id){
        try {
            return reviewMapper.toDTO(reviewService.ReadById(id));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get reviews by movie id", description = "Returns all reviews that are connected to movie found by given id")
    @GetMapping("movie/{movieID}")
    List<ReviewDTO> ReadByMovieId(@PathVariable Long movieID) {
        return reviewService.findAllByMovieId(movieID).stream()
                .map(reviewMapper::toDTO)
                .toList();
    }


    @Operation(summary = "Create review", description = "Returns newly created review")
    @PostMapping
    public ReviewDTO Create(@RequestBody ReviewDTO reviewDTO) {
        try {
            reviewDTO.setReviewId(null);
            return reviewMapper.toDTO(reviewService.Create(reviewMapper.toEntity(reviewDTO)));
        } catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update review", description = "Returns updated review found by given id")
    @PutMapping("/{id}")
    public ReviewDTO Update(@RequestBody ReviewDTO review, @PathVariable Long id)
    {
        try{
            Review newReview = reviewMapper.toEntity(review);
            newReview.setId(id);
            return reviewMapper.toDTO(reviewService.Update(newReview));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete review", description = "Deletes review found by given id")
    @DeleteMapping("/{id}")
    public void Delete(@PathVariable Long id)
    {
        try {
            reviewService.DeleteById(id);
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
