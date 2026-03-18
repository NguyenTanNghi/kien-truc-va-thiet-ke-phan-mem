package iuh.login.controller;

import iuh.login.model.User;
import iuh.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 3/18/2026 2:03 PM
 */
@RestController
@RequestMapping("/api/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserRepository repo;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody User req) {

        User user = repo.findByEmail(req.getEmail());

        if (user == null || !user.getPassword().equals(req.getPassword())) {
            return ResponseEntity.status(401).body("Sai email hoặc password");
        }

        return ResponseEntity.ok("Login success");
    }
}