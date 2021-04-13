package com.example.crud_react_js.controller;

import com.example.crud_react_js.beans.Person;
import com.example.crud_react_js.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonService personService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PersonController(PersonService personService) {
        this.personService = personService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAllPersons(){
        return personService.getPersons();
    }


    @PostMapping({"/login", "login"})
    public ResponseEntity<Object> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Person personInDB = personService.getPersonByUsername(username);

        //in case of giving username is not correct
        if (personInDB == null){
            return new ResponseEntity<>("The Username is not correct", HttpStatus.NOT_FOUND);
        //in case of giving username is correct
        }else {
            //in case of giving username is correct
            //and the password is not correct
            if (!passwordMatches(personInDB.getPassword(), password)){
                return new ResponseEntity<>("The Password is not correct", HttpStatus.NOT_FOUND);
            //in case of the giving password and username are correct
            }else{
                String token = getJWTToken(username);
            }
        }

        return new ResponseEntity<>("SDsds", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = {"/register", "register"} , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person register(@RequestBody Person person) {
        personService.savePerson(person);

        return person;
    }

    @RequestMapping("/a")
    private String getJWTToken(String username) {
        return "AAA";
    }

    public String encodePassword(String password) {

        String encodedPassword = this.bCryptPasswordEncoder.encode(password);

        System.out.println(encodedPassword);

        return encodedPassword;
    }

    public boolean passwordMatches(String encodedPassword, String userPassword){
        System.out.println("The password matches " + this.bCryptPasswordEncoder.matches(encodedPassword, userPassword));
        return this.bCryptPasswordEncoder.matches(encodedPassword, userPassword);
    }

    @RequestMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Person getPerson(@PathVariable("username") String username){
            return personService.getPersonByUsername(username);
    }
}
