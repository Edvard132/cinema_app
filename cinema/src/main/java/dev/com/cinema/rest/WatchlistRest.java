package dev.com.cinema.rest;

import dev.com.cinema.POJO.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/v1/user/watchlist")
@CrossOrigin(origins = "*")
public interface WatchlistRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addMovie(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/all")
    ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/delete")
    ResponseEntity<String> deleteMovie(@RequestBody(required = true) Map<String, String> requestMap);

}
