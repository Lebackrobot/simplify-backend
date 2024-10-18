package com.build.twitter_backend.repositories;

import com.build.twitter_backend.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserDetails findByUsername(String username);
}
