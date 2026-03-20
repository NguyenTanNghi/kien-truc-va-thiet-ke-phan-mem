package iuh.register.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

/**
 * @author Nguyen Tan Nghi
 * @created 3/18/2026 1:56 PM
 * @version 1.0
 */
@Entity
@ToString
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

}