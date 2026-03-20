package iuh.register.repository;

import iuh.register.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 3/18/2026 1:57 PM
 */
public interface UserRepository extends JpaRepository<User, Long> {
}