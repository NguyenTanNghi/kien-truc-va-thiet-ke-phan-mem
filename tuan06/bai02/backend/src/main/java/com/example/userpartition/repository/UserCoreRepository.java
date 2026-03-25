package com.example.userpartition.repository;

import com.example.userpartition.entity.UserCore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCoreRepository extends JpaRepository<UserCore, Long> {
    Optional<UserCore> findByEmail(String email);

    boolean existsByEmail(String email);
}
