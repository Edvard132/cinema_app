package dev.com.cinema.serviceImpl;

import dev.com.cinema.POJO.Movie;
import dev.com.cinema.POJO.Review;
import dev.com.cinema.POJO.User;
import dev.com.cinema.POJO.Watchlist;
import dev.com.cinema.constants.CinemaConstants;
import dev.com.cinema.dao.MovieRepository;
import dev.com.cinema.dao.UserRepository;
import dev.com.cinema.dao.WatchlistRepository;
import dev.com.cinema.service.WatchlistService;
import dev.com.cinema.utils.CinemaUtils;
import jdk.jshell.execution.Util;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public ResponseEntity<String> addMovie(Map<String, String> requestMap) {
        try {
                Optional<Movie> movie = movieRepository.findMovieByImdbId(requestMap.get("imdbId"));

                if (movie.isPresent()) {
                    Movie movieToBeAdded = movie.get();

                    Optional<Watchlist> watchlist = watchlistRepository.findById(new ObjectId(requestMap.get("watchListId")));

                    if (watchlist.isPresent()){
                        Watchlist watchlist1 = watchlist.get();
                        List<Movie> movies = watchlist1.getMovies();
                        boolean movieExistsInWatchlist = movies.stream()
                                .anyMatch(existingMovie -> existingMovie.getImdbId().equals(movieToBeAdded.getImdbId()));

                        if (movieExistsInWatchlist){
                            return new ResponseEntity<>("Movie already in watchlist", HttpStatus.OK);
                        }
                        mongoTemplate.update(Watchlist.class)
                                .matching(Criteria.where("Id").is(requestMap.get("watchListId")))
                                .apply(new Update().push("movies").value(movieToBeAdded))
                                .first();

                        return CinemaUtils.getResponseEntity("Movie " + movieToBeAdded.getTitle() + " added the watchlist", HttpStatus.OK);

                    }
                }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<Movie>> getAllMovies(Map<String, String> requestMap) {
        try {
            Optional<Watchlist> optional = watchlistRepository.findById(new ObjectId(requestMap.get("watchListId")));

            if (optional.isPresent()) {
                Watchlist watchlist = optional.get();
                return new ResponseEntity<>(watchlist.getMovies(), HttpStatus.OK);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteMovie(Map<String, String> requestMap) {
        log.info(requestMap.get("watchListId"));
        Optional<Watchlist> optional = watchlistRepository.findById(new ObjectId(requestMap.get("watchListId")));
        if (optional.isPresent()) {
            Watchlist watchlist = optional.get();
            Optional<Movie> optMovie = movieRepository.findMovieByImdbId(requestMap.get("imdbId"));
            if (optMovie.isPresent()){
                Movie movieTobeDeleted = optMovie.get();
                if (watchlist.getMovies().contains(movieTobeDeleted)) {
                    mongoTemplate.update(Watchlist.class)
                            .matching(Criteria.where("Id").is(requestMap.get("watchListId")))
                            .apply(new Update().pull("movies", movieTobeDeleted))
                            .first();
                    return new ResponseEntity<>("Movie deleted", HttpStatus.OK);

                }
                return new ResponseEntity<>("Movie not in watchlist", HttpStatus.OK);

            }

        }
        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

