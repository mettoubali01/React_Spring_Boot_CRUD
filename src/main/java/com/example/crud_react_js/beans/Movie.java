package com.example.crud_react_js.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    @JoinTable(
            name = "rel_movie_actor",
            joinColumns = @JoinColumn(name = "fk_movie", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "fk_actor", nullable = false)
    )

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Actor> actors;

    @ManyToOne
    @JoinColumn(name = "username")
    private Person person;

    public void addActor(Actor actor){
        if (actors == null)
            this.actors = new ArrayList<>();

        this.actors.add(actor);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", actors=" + actors +
                '}';
    }
}
