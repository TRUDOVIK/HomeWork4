package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.database.model.User;
import root.dto.UserLoginRequest;
import root.dto.UserRegisterRequest;
import root.service.SessionService;
import root.service.UserService;
import root.util.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userService.registerUser(userRegisterRequest)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Register error.");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        User user = userService.loginUser(userLoginRequest);
        if (user != null) {
            String token = jwtUtils.generateToken(user);
            sessionService.registerNewSession(token,user);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(responseHeaders).body("Login successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login error.");
        }

    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.getUsernameFromToken(token);
                User user = userService.findByUsername(username);
                if (user != null) {
                    return ResponseEntity.ok(user);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
