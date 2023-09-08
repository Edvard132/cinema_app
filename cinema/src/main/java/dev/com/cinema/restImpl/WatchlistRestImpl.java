package dev.com.cinema.restImpl;

import dev.com.cinema.POJO.Movie;
import dev.com.cinema.constants.CinemaConstants;
import dev.com.cinema.rest.WatchlistRest;
import dev.com.cinema.service.WatchlistService;
import dev.com.cinema.utils.CinemaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class WatchlistRestImpl implements WatchlistRest {

    @Autowired
    WatchlistService watchlistService;

    @Override
    public ResponseEntity<String> addMovie(Map<String, String> requestMap) {
        try {
            return watchlistService.addMovie(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Movie>> getAllMovies(Map<String, String> requestMap) {
        try {
            return watchlistService.getAllMovies(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteMovie(Map<String, String> requestMap) {
        try {
            return watchlistService.deleteMovie(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
