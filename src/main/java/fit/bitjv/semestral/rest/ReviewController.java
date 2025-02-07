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
    ReviewService reviewServiceService;
    MovieService movieService;
    private final ReviewMapper reviewMapper;


    public ReviewController(ReviewService reviewServiceService, MovieService movieService) {
        this.reviewServiceService = reviewServiceService;
        this.movieService = movieService;
        reviewMapper = new ReviewMapper(movieService);
    }

    @GetMapping
    List<ReviewDTO> ReadAll() {
        return reviewServiceService.ReadAll().stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    ReviewDTO ReadByID(@PathVariable Long id){
        try {
            return reviewMapper.toDTO(reviewServiceService.ReadById(id));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping
    public ReviewDTO Create(@RequestBody ReviewDTO reviewDTO) {
        try {
            return reviewMapper.toDTO(reviewServiceService.Create(reviewMapper.toEntity(reviewDTO)));
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
            return reviewMapper.toDTO(reviewServiceService.Update(newReview));
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
            reviewServiceService.DeleteById(id);
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
