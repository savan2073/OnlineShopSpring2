package com.example.OnlineShop.Controllers;

import com.example.OnlineShop.Model.Cart;
import com.example.OnlineShop.Model.Product;
import com.example.OnlineShop.Repositories.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") int id, HttpSession session){
        Product product = productRepository.getById(id);
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart();
        }
        cart.getProducts().add(product);
        session.setAttribute("cart", cart);
        return "redirect:/products/page/1";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductToCart(@PathVariable("id") int id, HttpSession session){
        Product product = productRepository.getById(id);
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart != null){
            cart.removeProduct(product);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }
}
