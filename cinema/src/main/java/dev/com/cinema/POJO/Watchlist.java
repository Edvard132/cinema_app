package dev.com.cinema.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "watchlist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Watchlist {
    @Id
    private ObjectId id;
    @DocumentReference
    private List<Movie> movies;
//    @DocumentReference
//    private User user;
    public Watchlist(List<Movie> movies) {
        this.movies = movies;
//        this.user = user;
    }
}