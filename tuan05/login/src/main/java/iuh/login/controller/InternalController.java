package iuh.login.controller;

import iuh.login.model.User;
import iuh.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 3/18/2026 2:02 PM
 */
@RestController
@RequestMapping("/api/internal")
public class InternalController {

    @Autowired
    private UserRepository repo;

    @PostMapping("/save-user")
    public void saveUser(@RequestBody User user) {
        repo.save(user);
    }
}