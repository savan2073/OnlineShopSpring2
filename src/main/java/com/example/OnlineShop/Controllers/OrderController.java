package com.example.OnlineShop.Controllers;

import com.example.OnlineShop.Model.Cart;
import com.example.OnlineShop.Model.Order;
import com.example.OnlineShop.Model.User;
import com.example.OnlineShop.Repositories.OrderRepository;
import com.example.OnlineShop.Repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cart cart;

    @GetMapping("/checkout")
    public String checkoutPage(){
        return "/checkout";
    }
    @PostMapping("/checkout")
    public String checkout(Principal principal, HttpSession session){
        cart = (Cart) session.getAttribute("cart");
        if(cart != null){
            Order order = new Order();
            order.setUser(userRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found")));
            order.setProducts(cart.getProducts());
            orderRepository.save(order);
            cart.clear();
        }
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model){
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }
}
