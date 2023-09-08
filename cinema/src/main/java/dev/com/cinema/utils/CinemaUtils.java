package dev.com.cinema.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CinemaUtils {
    private CinemaUtils(){}

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("\"message\":\""+responseMessage+"\"", httpStatus);
    }
}
