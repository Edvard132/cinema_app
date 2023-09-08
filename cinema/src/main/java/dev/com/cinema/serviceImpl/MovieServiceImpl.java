package dev.com.cinema.serviceImpl;

import dev.com.cinema.POJO.Movie;
import dev.com.cinema.service.MovieService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/movies")
@CrossOrigin(origins = "*")
public class MovieServiceImpl {

    @Autowired
    private MovieService movieService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String filterValue){
        try{
            return new ResponseEntity<List<Movie>>(movieService.getAllMovies(filterValue), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Movie>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Movie>>(movieService.getSingleMovie(imdbId), HttpStatus.OK);
    }


}
