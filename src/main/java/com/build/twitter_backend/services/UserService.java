package com.build.twitter_backend.services;

import com.build.twitter_backend.models.UserModel;
import com.build.twitter_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public UserDetails getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDetails create(UserModel userModel) {
        return userRepository.save(userModel);
    }
}
