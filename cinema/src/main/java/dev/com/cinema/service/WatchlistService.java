package dev.com.cinema.service;

import dev.com.cinema.POJO.Movie;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface WatchlistService {

    ResponseEntity<String> addMovie(Map<String, String> requestMap);

    ResponseEntity<List<Movie>> getAllMovies(Map<String, String> requestMap);

    ResponseEntity<String> deleteMovie(Map<String, String> requestMap);

}
