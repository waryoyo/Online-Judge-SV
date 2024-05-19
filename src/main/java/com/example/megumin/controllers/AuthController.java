package com.example.megumin.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        // Check if the authenticated principal is not "anonymousUser"
        Object principal = authentication.getPrincipal();
        return principal instanceof UserDetails;
    }
    @GetMapping("/login")
    public String getLoginPage() {
        if (isAuthenticated()) {
            return "redirect:/problems";
        }

        return "auth/login";
    }
    @RequestMapping("/signup")
    public String getSignupPage() {
        return "auth/signup";
    }
}
