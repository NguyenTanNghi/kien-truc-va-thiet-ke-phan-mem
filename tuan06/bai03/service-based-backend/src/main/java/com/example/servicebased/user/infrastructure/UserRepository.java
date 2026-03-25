package com.example.servicebased.user.infrastructure;

import com.example.servicebased.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
