package com.example.OnlineShop.Controllers;

import com.example.OnlineShop.Model.User;
import com.example.OnlineShop.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegisterUser(@ModelAttribute User newUser){
        if(userRepository.findByUsername(newUser.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username already taken");
        }
        userRepository.addUser(newUser);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user){

        return "redirect:/";
    }
}
