package com.example.megumin.apiControllers;

import com.example.megumin.DTO.LoginDTO;
import com.example.megumin.DTO.SignupDTO;
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

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
        try {
//            System.out.println(loginDTO.getUsernameOrEmail());
//            System.out.println(loginDTO.getPassword());
//            System.out.println(passwordEncoder.encode(loginDTO.getPassword()));
//            System.out.println(passwordEncoder.matches(loginDTO.getPassword(), "$2a$10$g29Qf1wN8jpjaQmT3HwrauhitO29jrs5IZH0CK8h8fR1vp3BLXh92"));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
//            System.err.println("Logging in...2");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("User DIDNT signed-in successfully!.", HttpStatus.BAD_GATEWAY);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signupDTO){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signupDTO.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signupDTO.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setFirstName(signupDTO.getFirstName());
        user.setLastName(signupDTO.getLastName());
        user.setUsername(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setRole(Role.SOLVER);

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

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
