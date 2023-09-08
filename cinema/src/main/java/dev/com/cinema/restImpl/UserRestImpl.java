package dev.com.cinema.restImpl;

import dev.com.cinema.constants.CinemaConstants;
import dev.com.cinema.rest.UserRest;
import dev.com.cinema.service.UserService;
import dev.com.cinema.utils.CinemaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> getWatchlistId(Map<String, String> requestMap) {
        try {
            return userService.getWatchlistId(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
