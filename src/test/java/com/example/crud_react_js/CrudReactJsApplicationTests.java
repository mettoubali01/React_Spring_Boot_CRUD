package com.example.crud_react_js;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.crud_react_js.configuration.PasswordConfig.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CrudReactJsApplicationTests {


    public static void main(String... arg){
        String password = "aaa";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String encondedPassword = bCryptPasswordEncoder.encode(password);

        System.out.println(encondedPassword);

    }

    @Test
    void contextLoads() {
        System.out.println("Abday");
    }
}
