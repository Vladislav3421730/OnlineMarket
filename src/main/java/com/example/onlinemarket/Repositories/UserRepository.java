package com.example.onlinemarket.Repositories;

import com.example.onlinemarket.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     User findUserByLogin(String login);
}
