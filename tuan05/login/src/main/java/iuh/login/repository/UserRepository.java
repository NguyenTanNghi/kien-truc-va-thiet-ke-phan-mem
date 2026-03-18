package iuh.login.repository;

import iuh.login.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 3/18/2026 2:01 PM
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}