package com.vishesh.aiinterviewassistant.repository;

import com.vishesh.aiinterviewassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findTop10ByOrderByIdDesc();
}