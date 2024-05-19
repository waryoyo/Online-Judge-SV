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
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsernameOrEmail(), passwordEncoder.encode(loginDTO.getPassword())));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);

    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupDTO signupDTO) {
        if(userRepository.existsByUserName(signupDTO.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signupDTO.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFirstName(signupDTO.getFirstName());
        user.setLastName(signupDTO.getLastName());

        user.setUserName(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        user.setRole(Role.SOLVER);

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
