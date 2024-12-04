package com.example.easyfix.repository;

import com.example.easyfix.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepo extends JpaRepository<Services, Integer> {
}
