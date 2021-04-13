package com.example.crud_react_js.beans;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Person {
    @Id
    private String username;

    private String name;
    private String email;
    private String password;

    /*@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Movie> movies;
*/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "persons_roles",
            joinColumns = @JoinColumn(
                    name = "username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Person(){}

    public Person(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
