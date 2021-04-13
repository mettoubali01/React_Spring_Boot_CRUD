package com.example.crud_react_js.service;

import com.example.crud_react_js.beans.Authority;
import com.example.crud_react_js.beans.Person;
import com.example.crud_react_js.beans.Role;
import com.example.crud_react_js.respository.IPersonRepository;
import com.google.common.base.Strings;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {

    private final IPersonRepository iPersonRepository;
    private final List<Person> personArrayList = Arrays.asList(
            new Person("Med01", "Muhammad", "m@m.com", "password"),
            new Person("mari01", "Mariem", "ma@ma.com", "password"),
            new Person("moo01", "Mooo", "mo@mo.com", "password")
    );

    public PersonService(IPersonRepository iPersonRepository) {
        this.iPersonRepository = iPersonRepository;
    }

    @Override
    public Person getPersonByUsername(String username) {
        /*return personArrayList.stream()
                .filter(person -> username.equals(person.getUsername()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Person " + username + " does not exists"
                ));*/
        Person person = iPersonRepository.findById(username).orElse(null);

        return iPersonRepository.findById(username).orElse(null);
    }


    @Override
    public String getUserPwdByUsername(String username) {

        return iPersonRepository.getUserPwdByUsername(username);

    }

    @Override
    public List<Person> getPersons() {
        return iPersonRepository.findAll();
    }

    @Override
    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities(Person person) {

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        Collection<Role> authorities = person.getRoles();

        if (authorities.size() != 0)
            for (Role role : authorities) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                for (Authority authority : role.getPrivileges()) {
                    simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
                }
            }

        for (SimpleGrantedAuthority cad : simpleGrantedAuthorities) {
            System.out.println("authority " + cad.getAuthority());
        }

        return simpleGrantedAuthorities;
    }

    @Transactional
    @Override
    public void savePerson(Person person) {
        iPersonRepository.save(person);
    }

}
