package com.example.crud_react_js.service;

import com.example.crud_react_js.beans.Actor;
import com.example.crud_react_js.beans.Movie;
import com.example.crud_react_js.respository.IMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService implements IMovieService{

    private IMovieRepository iMovieRepository;

    public MovieService(IMovieRepository iMovieRepository) {
        this.iMovieRepository = iMovieRepository;
    }

    @Override
    public Optional<Movie> getMovieByName(String movieName) {
        return Optional.ofNullable(iMovieRepository.getMovieByTitle(movieName).orElse(null));
    }

    @Override
    public void saveMovie(Movie movie) {
        iMovieRepository.save(movie);
    }

    @Override
    public List<Movie> getMovies() {
        return iMovieRepository.findAll();
    }

    @Override
    public Set<Actor> getActorOfMovie(String movieName) {
        return (Set<Actor>) iMovieRepository.getMovieByTitle(movieName).get().getActors();
    }

    @Override
    public void deleteMovieByName(String movieName) {
        iMovieRepository.deleteByTitle(movieName);
    }

    @Override
    public void deleteMovieById(long id) {
        iMovieRepository.deleteById(id);
    }

    @Override
    public Movie getMovieById(Long id) {
        return iMovieRepository.findById(id).orElse(null);
    }
}
