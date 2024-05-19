package com.example.megumin.apiControllers;

import com.example.megumin.payloads.LoginDTO;
import com.example.megumin.payloads.SignupDTO;
import com.example.megumin.models.Role;
import com.example.megumin.models.User;
import com.example.megumin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthAPIController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping("/signup")
//    public String registerUser(@RequestBody SignupDTO signupDTO){
//
//        // add check for username exists in a DB
//        if(userRepository.existsByUsername(signupDTO.getUsername())){
//            return "Username is already taken!";
//        }
//
//        // add check for email exists in DB
//        if(userRepository.existsByEmail(signupDTO.getEmail())){
//            return "Email is already taken!";
//        }
//
//        // create user object
//        User user = new User();
//        user.setFirstName(signupDTO.getFirstName());
//        user.setLastName(signupDTO.getLastName());
//        user.setUsername(signupDTO.getUsername());
//        user.setEmail(signupDTO.getEmail());
//        user.setEnabled(true);
//        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
//        user.setRole(Role.SOLVER);
//
//        userRepository.save(user);
//
//        return "redirect:/login";
//
//    }

    @GetMapping("/user")
    public ResponseEntity<Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return new ResponseEntity<>(authentication.getName(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
