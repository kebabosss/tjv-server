package fit.bitjv.semestral.rest;


import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.rest.dto.ReviewDTO;
import fit.bitjv.semestral.rest.dto.ReviewMapper;
import fit.bitjv.semestral.service.MovieService;
import fit.bitjv.semestral.service.ReviewService;

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

    @GetMapping
    List<ReviewDTO> ReadAll() {
        return reviewService.ReadAll().stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

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

    @GetMapping("movie/{movieID}")
    List<ReviewDTO> ReadByMovieId(@PathVariable Long movieID) {
        return reviewService.findAllByMovieId(movieID).stream()
                .map(reviewMapper::toDTO)
                .toList();
    }



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
