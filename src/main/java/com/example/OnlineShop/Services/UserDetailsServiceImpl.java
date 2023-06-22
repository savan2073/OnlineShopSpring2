package com.example.OnlineShop.Services;

import com.example.OnlineShop.Model.User;
import com.example.OnlineShop.Repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.getByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return user;
    }
}
