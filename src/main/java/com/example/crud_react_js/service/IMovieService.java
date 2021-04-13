package com.example.crud_react_js.service;

import com.example.crud_react_js.beans.Actor;
import com.example.crud_react_js.beans.Movie;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IMovieService {
    Optional<Movie> getMovieByName(String movieName);
    void saveMovie(Movie movie);
    List<Movie> getMovies();
    Set<Actor> getActorOfMovie(String movieName);
    void deleteMovieByName(String movieName);
    void deleteMovieById(long id);
    Movie getMovieById(Long id);
}
