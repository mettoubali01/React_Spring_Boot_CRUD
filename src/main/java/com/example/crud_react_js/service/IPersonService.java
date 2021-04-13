package com.example.crud_react_js.service;

import com.example.crud_react_js.beans.Person;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

public interface IPersonService {
    Person getPersonByUsername(String username);
    String getUserPwdByUsername(String username);
    void savePerson(Person person);
    List<Person> getPersons();
    Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities(Person person);

}
