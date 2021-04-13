package com.example.crud_react_js.controller;

import com.example.crud_react_js.beans.Actor;
import com.example.crud_react_js.beans.Movie;
import com.example.crud_react_js.beans.Person;
import com.example.crud_react_js.service.IMovieService;
import com.example.crud_react_js.service.IPersonService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final IMovieService iMovieService;
    private final IPersonService iPersonService;

    public MovieController(IPersonService iPersonService, IMovieService iMovieService) {
        this.iMovieService = iMovieService;
        this.iPersonService = iPersonService;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Movie> getMovies() {
        return iMovieService.getMovies();
    }

    @GetMapping(path = "{movieName}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Movie getMovieByName(@PathVariable("movieName") String movieName) {
        return iMovieService.getMovieByName(movieName).orElse(null);
    }

    @PostMapping(path = "/add/{username}")
    private void addMovie(@PathVariable("username") String username, @RequestBody Movie movie) {
        System.out.println("Username " + movie);
        List<Actor> actors = movie.getActors();
        if (actors.size() > 0) {
            for (Actor actor : actors) {
                System.out.println("Actor " + actor.getName());
            }
        }
        Person person = iPersonService.getPersonByUsername(username);
        movie.setPerson(person);
        System.out.println("Moviee username "+ movie.getPerson().getUsername());
        iMovieService.saveMovie(movie);
    }

    @DeleteMapping(path = "/delete/{movieName}")
    private void removeMovieByTitle(@PathVariable("movieName") String movie) {
        iMovieService.deleteMovieByName(movie);
    }

    @DeleteMapping(path = "/remove/{id}")
    private void removeMovieById(@PathVariable("id") Long id) {
        iMovieService.deleteMovieById(id);
    }

    @PutMapping(path = "/update/{id}")
    private void updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
        Movie movie1 = iMovieService.getMovieById(id);

        movie1.setActors(movie.getActors());
        movie1.setTitle(movie.getTitle());
        movie1.setType(movie.getType());

        System.out.println("The existent movie " + movie1);

        List<Actor> actors = movie1.getActors();
        if (actors.size() > 0) {
            for (Actor actor : actors) {
                System.out.println("Actor " + actor.getName());
            }
        }
        System.out.println("Movie1 username "+ movie1.getPerson().getUsername());
        iMovieService.saveMovie(movie1);
    }
}
