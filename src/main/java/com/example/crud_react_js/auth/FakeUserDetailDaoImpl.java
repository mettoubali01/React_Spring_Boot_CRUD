package com.example.crud_react_js.auth;

import com.example.crud_react_js.beans.Person;
import com.example.crud_react_js.beans.Role;
import com.example.crud_react_js.respository.IPersonRepository;
import com.example.crud_react_js.service.IPersonService;
import com.example.crud_react_js.service.PersonService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.crud_react_js.configuration.UserRole.*;

@Repository("fake")
public class FakeUserDetailDaoImpl implements UserDetailDao {

    private final PasswordEncoder passwordEncoder;
    private final IPersonRepository iPersonRepository;
    private final PersonService personService;

    @Autowired
    public FakeUserDetailDaoImpl(IPersonRepository iPersonRepository,
                                 PasswordEncoder passwordEncoder,
                                 PersonService personService) {
        this.passwordEncoder = passwordEncoder;
        this.iPersonRepository = iPersonRepository;
        this.personService = personService;
    }

    @Override
    public UserDetail selectUserDetailByUsername(String username) {
        Person person = personService.getPersonByUsername(username);
        System.out.println("Before " + person.getPassword());
        System.out.println("EEEE " + personService.getSimpleGrantedAuthorities(person));
        UserDetail userDetail = new UserDetail(personService.getSimpleGrantedAuthorities(person),
                person.getPassword(),
                person.getUsername(),
                true,
                true,
                true,
                true);
        return userDetail;
        /*return getUsersDetails()
                .stream()
                .filter(userDetail -> username.equals(userDetail.getUsername()))
                .findFirst();*/
    }


    public List<UserDetail> getUsersDetails() {

        List<UserDetail> userDetailSet = new ArrayList<>();
        List<Person> personList = personService.getPersons();

        /*personList.stream()
                .map(person -> userDetailSet.add(new UserDetail()))*/

        userDetailSet = Lists.newArrayList(
                new UserDetail(STUDENT.getSimpleGrantedAuthorities(),
                        passwordEncoder.encode("aaa"),
                        "mariem",
                        true,
                        true,
                        true,
                        true),
                new UserDetail(ADMIN.getSimpleGrantedAuthorities(),
                        passwordEncoder.encode("aaa"),
                        "mohamed",
                        true,
                        true,
                        true,
                        true),
//                ADMINTRAINEE.getSimpleGrantedAuthorities()
                new UserDetail(null,
                        passwordEncoder.encode("aaa"),
                        "men",
                        true,
                        true,
                        true,
                        true)
                );

        return userDetailSet;
    }
}
