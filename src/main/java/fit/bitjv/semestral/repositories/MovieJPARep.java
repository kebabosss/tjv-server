package fit.bitjv.semestral.repositories;

import fit.bitjv.semestral.domain.Movie;
import fit.bitjv.semestral.domain.Review;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

public class MovieJPARep implements MovieDAO{
    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public List<Movie> allMovies() {
        TypedQuery<Movie> q = em.createNamedQuery("allMovies", Movie.class);
        return q.getResultList();

    }

    @Override
    @Transactional
    public Movie findMovie(Long movieId) {
        return em.find(Movie.class, movieId);
    }

    @Override
    @Transactional
    public Long createMovie(Movie ent) {
        em.persist(ent);
        em.flush();
        return ent.getId();
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        Movie m = findMovie(id);
        em.remove(m);
    }

    @Override
    public List<Review> allReviews() {
        TypedQuery<Review> q = em.createNamedQuery("allReviews", Review.class);
        return q.getResultList();

    }

    @Override
    public Long createReview(Review ent) {
        em.persist(ent);
        em.flush();
        return ent.getId();
    }

    @Override
    public List<Review> reviewsForMovieId(Long movieId) {
        TypedQuery<Review> tq = em.createNamedQuery("reviewsForMovieId", Review.class);
        tq.setParameter("movieId", movieId);
        return tq.getResultList();

    }

    @Override
    public void deleteReview(Long id) {
        Review review = em.find(Review.class, id);
        em.remove(review);


    }
}
