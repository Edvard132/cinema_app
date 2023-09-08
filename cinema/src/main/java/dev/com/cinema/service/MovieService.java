package dev.com.cinema.service;

import dev.com.cinema.CinemaApplication;
import dev.com.cinema.POJO.Movie;
import dev.com.cinema.constants.CinemaConstants;
import dev.com.cinema.dao.MovieRepository;
import dev.com.cinema.serviceImpl.UserServiceImpl;
import dev.com.cinema.utils.CinemaUtils;
import org.apache.logging.log4j.util.Strings;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService{
    private static final Logger log = LoggerFactory.getLogger(MovieService.class);


    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies(String filterValue){
        try{
            if (filterValue != null){
                return movieRepository.getFilteredMovies(filterValue);
            }
            return movieRepository.findAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<Movie> getSingleMovie(String imdbId){

        return movieRepository.findMovieByImdbId(imdbId);
    }
}
