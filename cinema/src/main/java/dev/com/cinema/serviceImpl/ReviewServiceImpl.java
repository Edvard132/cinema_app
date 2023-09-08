package dev.com.cinema.serviceImpl;

import dev.com.cinema.POJO.Review;
import dev.com.cinema.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewServiceImpl {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload){
        try {
            return new ResponseEntity<Review>(reviewService.createReview(payload.get("reviewBody"), payload.get("imdbId")), HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
