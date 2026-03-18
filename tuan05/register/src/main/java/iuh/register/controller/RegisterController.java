package iuh.register.controller;

import iuh.register.model.User;
import iuh.register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 3/18/2026 1:59 PM
 */
@RestController
@RequestMapping("/api/register")
@CrossOrigin("*")
public class RegisterController {

    @Autowired
    private RegisterService service;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println(user);
        return ResponseEntity.ok(service.register(user));
    }
}