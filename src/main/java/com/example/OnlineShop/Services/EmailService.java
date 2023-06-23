package com.example.OnlineShop.Services;

import com.example.OnlineShop.Model.Order;
import com.example.OnlineShop.Model.Product;
import com.example.OnlineShop.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(User user, Order order){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Order confirmation");
        StringBuilder mailContent = new StringBuilder();
        mailContent.append("Użytkowniku " + user.getUsername() + "dziękuję za zamówienie o id: " + order.getId());
        mailContent.append("Twoje zamówienie składa się z:\n");

        for (Product product: order.getProducts()){
            mailContent.append("Product name: " + product.getName() + "\n");
            mailContent.append("Product price: " + product.getPrice() + "\n");
            mailContent.append("Product description: " + product.getDescription() + "\n");
            mailContent.append("--------------------------------\n");
        }

        message.setText(mailContent.toString());
        mailSender.send(message);
    }
}
