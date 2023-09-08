package dev.com.cinema.dao;

import dev.com.cinema.POJO.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {

    Optional<Movie> findMovieByImdbId(String imdbId);

    @Query("{'title': {$regex: '?0', $options: 'i'}}")
    List<Movie> getFilteredMovies(String filterValue);
}
