package com.example.crud_react_js.respository;

import com.example.crud_react_js.beans.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long>    {

    @Query("select m from Movie m where m.title = ?1")
    Optional<Movie> getMovieByTitle(String name );

    @Query("delete from Movie m where m.title =?1")
    void deleteByTitle(String movieName);
}
