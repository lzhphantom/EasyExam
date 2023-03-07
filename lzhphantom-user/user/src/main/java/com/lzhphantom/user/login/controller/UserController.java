package com.lzhphantom.user.login.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class UserController {
    @GetMapping("/test")
    public String test(){
        return "test ok";
    }
}
