package iuh.register.service;

import iuh.register.model.User;
import iuh.register.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 3/18/2026 1:58 PM
 */
@Service
public class RegisterService {

    @Autowired
    private UserRepository repo;

    public String register(User user) {

//        System.out.println(user);
        repo.save(user);
        System.out.println("Register success");

        // gọi login-service để sync
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(
                "http://localhost:8081/api/internal/save-user",
                user,
                String.class
        );

        return "Register success";
    }
}