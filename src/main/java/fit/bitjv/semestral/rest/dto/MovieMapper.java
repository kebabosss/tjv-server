package fit.bitjv.semestral.rest.dto;

import fit.bitjv.semestral.domain.Director;
import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import fit.bitjv.semestral.service.DirectorServices;
import fit.bitjv.semestral.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper {
    DirectorServices directorServices;
    ReviewService reviewService;

    public MovieMapper(DirectorServices directorServices, ReviewService reviewService) {
        this.directorServices = directorServices;
        this.reviewService = reviewService;
    }

    public Movie toEntity(MovieDTO movieDTO)
    {
        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setName(movieDTO.getName());
        movie.setReleaseYear(movieDTO.getReleaseYear());

        List<Director> directors = directorServices.findAllById(movieDTO.getDirectors());
        movie.setDirectors(directors);
        if (movieDTO.getReviews() != null) {
            List<Review> reviews = reviewService.findAllById(movieDTO.getReviews());
            movie.setReviews(reviews);
        }

        return movie;
    }

    public MovieDTO toDTO(Movie movie)
    {
        MovieDTO tmpDTO = new MovieDTO();
        tmpDTO.setId(movie.getId());
        tmpDTO.setName(movie.getName());
        tmpDTO.setReleaseYear(movie.getReleaseYear());
        List<Long> tmpDirectors = new ArrayList<>();
        for (int i = 0; i < movie.getDirectors().size(); i++) {
            tmpDirectors.add(movie.getDirectors().get(i).getId());
        }
        tmpDTO.setDirectors(tmpDirectors);
        if(movie.getReviews() != null) {
            List<Long> tmpReviews = new ArrayList<>();
            for (Review review : movie.getReviews()) {
                tmpReviews.add(review.getId());
            }
            tmpDTO.setReviews(tmpReviews);
        }
        return tmpDTO;
    }
}
