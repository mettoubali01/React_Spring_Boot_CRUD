package com.example.crud_react_js.respository;

import com.example.crud_react_js.beans.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends JpaRepository<Person, String> {
    Person getPersonByUsername(String username);
    @Query("select p.password from Person p where p.username = ?1")
    String getUserPwdByUsername(String username);
}
