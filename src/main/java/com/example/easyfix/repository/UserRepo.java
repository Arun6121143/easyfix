package com.example.easyfix.repository;

import com.example.easyfix.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    Boolean existsByEmail(final String email);
    User findByEmail(final String email);
}
