package com.example.OnlineShop.Repositories;

import com.example.OnlineShop.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll(){
        List<User> query = jdbcTemplate.query("SELECT id, username, email, password FROM user",
                BeanPropertyRowMapper.newInstance(User.class));
        return query;
    }

    public User getByUsername(String username){
        return jdbcTemplate.queryForObject("SELECT id, username, email, password FROM user WHERE username = ?",
                BeanPropertyRowMapper.newInstance(User.class), username);
    }
    public Optional<User> findByUsername(String username){
        String sql = "SELECT * FROM user WHERE username = ?";
        try{
            User user = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), username);
            return Optional.ofNullable(user);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    public int addUser(User user){
        return jdbcTemplate
                .update("INSERT INTO user(username, email, password) VALUES(?, ?, ?)",
                        user.getUsername(), user.getEmail(), user.getPassword());
    }
}
