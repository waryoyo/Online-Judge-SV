package com.example.megumin.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String getHomepage() {
        return "main";
    }

    @RequestMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    @RequestMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }
}
