package com.example.OnlineShop.Configuration;

import com.example.OnlineShop.Model.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Cart cart(){
        return new Cart();
    }
}
