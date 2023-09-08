package dev.com.cinema.dao;

import dev.com.cinema.POJO.Movie;
import dev.com.cinema.POJO.Watchlist;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchlistRepository extends MongoRepository<Watchlist, ObjectId> {

    Optional<Watchlist> findById(ObjectId id);
}
