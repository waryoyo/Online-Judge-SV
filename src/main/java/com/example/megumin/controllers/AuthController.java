package com.example.megumin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class AuthController {
    @RequestMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }
    @RequestMapping("/signup")
    public String getSignupPage() {
        return "auth/signup";
    }
}
