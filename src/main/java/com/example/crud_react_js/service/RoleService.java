package com.example.crud_react_js.service;

import com.example.crud_react_js.beans.Person;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

public class RoleService implements IRoleService{
    @Override
    public String getRoleByUsername(String username) {
        return null;
    }

    @Override
    public void savePerson(Person person) {

    }

    @Override
    public List<Person> getPersons() {
        return null;
    }

    @Override
    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        return null;
    }
}
