package com.projeto.APITaskManager.repository;

import com.projeto.APITaskManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByName(String name);
    Optional<User> findById(Long id);
    boolean existsById(Long id);
}
