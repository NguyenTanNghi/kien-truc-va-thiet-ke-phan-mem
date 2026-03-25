package com.example.userpartition.repository;

import com.example.userpartition.entity.UserFemale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFemaleRepository extends JpaRepository<UserFemale, Long> {
    Optional<UserFemale> findByEmail(String email);

    boolean existsByEmail(String email);
}
