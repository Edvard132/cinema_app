package dev.com.cinema.serviceImpl;

import dev.com.cinema.JWT.JwtService;
import dev.com.cinema.LoginResponse;
import dev.com.cinema.POJO.Movie;
import dev.com.cinema.POJO.Role;
import dev.com.cinema.POJO.User;
import dev.com.cinema.POJO.Watchlist;
import dev.com.cinema.constants.CinemaConstants;
import dev.com.cinema.dao.UserRepository;
import dev.com.cinema.dao.WatchlistRepository;
import dev.com.cinema.service.UserService;
import dev.com.cinema.utils.CinemaUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            if (validateSignupMap(requestMap)){
                Optional<User> optional = userRepository.findByEmail(requestMap.get("email"));
                if (optional.isEmpty()){
                    User user = getUserFromMap(requestMap);
                    userRepository.insert(user);
                    String token = jwtService.generateToken(user);
                    return new ResponseEntity<String>(token, HttpStatus.CREATED);
                }
                else {
                    return new ResponseEntity<String>("User with this email already exists.", HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return new ResponseEntity<String>("Invalid data", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestMap.get("email"), requestMap.get("password")
                    )
            );
//            Optional<User> optional = userRepository.findByEmail(requestMap.get("email"));
//        if (optional.isPresent()){
//            User user = optional.get();
//                String password = requestMap.get("password");
//                String encodedPassword = user.getPassword();
//                boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//                if (isPwdRight) {
//                    return new ResponseEntity<String>("Login success.", HttpStatus.OK);
//
//                } else {
//
//                    return new ResponseEntity<String>("Invalid password", HttpStatus.OK);
//                }
//            }else {
//                return new ResponseEntity<String>("User does not exist", HttpStatus.OK);
//            }
            Optional<User> optional = userRepository.findByEmail(requestMap.get("email"));
            if (optional.isPresent()) {
                User user = optional.get();
                String token = jwtService.generateToken(user);
                return new ResponseEntity<String>(token, HttpStatus.OK);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> getWatchlistId(Map<String, String> requestMap) {
        try {
            Optional<User> optional = userRepository.findByEmail(requestMap.get("email"));

            if (optional.isPresent()){
                User user = optional.get();
                return new ResponseEntity<String>(String.valueOf(user.getWatchlist().getId()), HttpStatus.OK);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CinemaUtils.getResponseEntity(CinemaConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateSignupMap(Map<String, String> requestMap){
        return requestMap.containsKey("name") && requestMap.containsKey("email") &&
                requestMap.containsKey("password");
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        Watchlist watchlist = new Watchlist(new ArrayList<Movie>());
        watchlistRepository.insert(watchlist);
        user.setRole(Role.USER);
        user.setWatchlist(watchlist);
        return user;
    }

}
