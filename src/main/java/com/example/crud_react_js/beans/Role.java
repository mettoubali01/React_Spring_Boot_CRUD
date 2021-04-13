package com.example.crud_react_js.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<Person> users;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Authority> privileges;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Collection<Person> getUsers() {
        return users;
    }

    public void setUsers(Collection<Person> users) {
        this.users = users;
    }*/

    public Collection<Authority> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Authority> privileges) {
        this.privileges = privileges;
    }

    /*@Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", privileges=" + privileges +
                '}';
    }*/
}