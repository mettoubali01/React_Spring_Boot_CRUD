package com.example.crud_react_js.auth;

import com.example.crud_react_js.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    private final FakeUserDetailDaoImpl fakeUserDetailDao;

    @Autowired
    public UserDetailService(FakeUserDetailDaoImpl userDetailDao,
                             PersonService personService){
        this.fakeUserDetailDao = userDetailDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return fakeUserDetailDao
                .selectUserDetailByUsername(username)
                /*.orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username))
                )*/;
    }
}
