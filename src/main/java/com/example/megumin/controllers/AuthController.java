package com.example.megumin.controllers;

import com.example.megumin.models.Role;
import com.example.megumin.models.User;
import com.example.megumin.payloads.SignupDTO;
import com.example.megumin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("signupDTO", new SignupDTO());
        return "auth/signup";
    }
    @PostMapping("/signup")
    public String getSignupPage(@ModelAttribute("signupDTO") SignupDTO signupDTO, Model model) {
        if (userRepository.existsByUsername(signupDTO.getEmail())) {
            model.addAttribute("error", "Username is already taken!");
            return "auth/signup";
        }

        // Check if email exists in the DB
        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            model.addAttribute("error", "Email is already taken!");
            return "auth/signup";
        }

        User user = new User();
        user.setFirstName(signupDTO.getFirstName());
        user.setLastName(signupDTO.getLastName());
        user.setUsername(signupDTO.getEmail());
        user.setEmail(signupDTO.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setRole(Role.SOLVER);

        userRepository.save(user);
        return "redirect:/login";

    }
}
