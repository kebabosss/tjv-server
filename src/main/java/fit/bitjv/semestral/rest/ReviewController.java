package fit.bitjv.semestral.rest;


import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.rest.dto.ReviewDTO;
import fit.bitjv.semestral.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("rest/review")
public class ReviewController {
    ReviewService reviewServiceService;
    private final ModelMapper modelMapper;


    public ReviewController(ReviewService reviewServiceService, ModelMapper modelMapper) {
        this.reviewServiceService = reviewServiceService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    List<ReviewDTO> ReadAll() {
        return ConvertManyToDTO(reviewServiceService.ReadAll());
    }

    @GetMapping("/{id}")
    ReviewDTO ReadByID(@PathVariable Long id){
        try {
            return ConvertToDTO(reviewServiceService.ReadById(id));
        }
        catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping
    public Review Create(@RequestBody ReviewDTO reviewDTO) {
        try {
            return reviewServiceService.Create(ConvertToEntity(reviewDTO));
        } catch (IllegalArgumentException e)
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public Review Update(@RequestBody ReviewDTO review, @PathVariable Long id)
    {
        try{
            Review newReview = ConvertToEntity(review);
            newReview.setId(id);
            return reviewServiceService.Update(ConvertToEntity(ConvertToDTO(newReview)));
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

    private ReviewDTO ConvertToDTO(Review review)
    {
        return modelMapper.map(review, ReviewDTO.class);
    }

    private Review ConvertToEntity(ReviewDTO reviewDTO)
    {
        return modelMapper.map(reviewDTO, Review.class);
    }

    private List<ReviewDTO> ConvertManyToDTO(List<Review> reviews) {
        return reviews.stream()
                .map(this::ConvertToDTO)
                .toList();
    }
}
