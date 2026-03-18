package iuh.login.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Nguyen Tan Nghi
 * @created 3/18/2026 2:00 PM
 * @version 1.0
 */
@Getter
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String email;
    private String password;
}