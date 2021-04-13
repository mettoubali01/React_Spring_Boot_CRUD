package com.example.crud_react_js.controller;

import com.example.crud_react_js.beans.Person;
import com.example.crud_react_js.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/management/api/v1/persons")
public class StudentManagementController {

    @Autowired
    private IPersonService iPersonService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE', 'ROLE_STUDENT')")
    public List<Person> getAllPerson(){
        return iPersonService.getPersons();
    }

    @GetMapping("{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public Person getPersonByUsername(@PathVariable("username") String username){
        System.out.print("Search for person by his username: ");
        return iPersonService.getPersonByUsername(username);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewPerson(@RequestBody Person person){
        System.out.print("Register new Person: ");
        System.out.println(person);
    }

    @DeleteMapping("{username}")
    @PreAuthorize("hasAnyAuthority('student:read', 'student:write', 'courses:read', 'courses:write')")
    public ResponseEntity deletePerson(@PathVariable("username") String username){
        System.out.print("delete person: ");
        if (1+1 == 2)
            return ResponseEntity.ok()
                .header("Custom-Header", "foo")
                .body("Custom header set");
        else
            return new ResponseEntity<>("No ", HttpStatus.OK);

    }

    @PutMapping("{username}")
    @PreAuthorize("hasAuthority('student:read')")
    public void updatePerson(@PathVariable("username") String username, @RequestBody Person person){
        System.out.print("Updating: ");
        System.out.println(String.format("%s %s",username, person));
    }
}
