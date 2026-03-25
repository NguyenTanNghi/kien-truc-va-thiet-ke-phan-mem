package com.example.userpartition.repository;

import com.example.userpartition.entity.UserMale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMaleRepository extends JpaRepository<UserMale, Long> {
    Optional<UserMale> findByEmail(String email);

    boolean existsByEmail(String email);
}
