package com.example.crud_react_js.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PController {

    @GetMapping("login")
    public String retrieveLoginPage(){
        return "login";
    }

    @GetMapping("welcome")
    public String retrieveWPage(){
        return "welcome";
    }
}
