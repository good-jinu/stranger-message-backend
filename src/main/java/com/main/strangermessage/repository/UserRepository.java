package com.main.strangermessage.repository;

import com.main.strangermessage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);
    List<User> findByCreatedAtLessThanOrderByIdAsc(Timestamp createdAt);
}
