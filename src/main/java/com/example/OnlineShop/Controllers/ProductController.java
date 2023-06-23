package com.example.OnlineShop.Controllers;

import com.example.OnlineShop.Model.Product;
import com.example.OnlineShop.Repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public List<Product> getAll(){
        return productRepository.getAll();
    }
    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") int id){
        return productRepository.getById(id);
    }
    @GetMapping("/addProduct")
    public String addProduct(Model model){
        logger.info("addProduct called");
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String submitAddProduct(@ModelAttribute Product product){
        logger.info("submitAddProduct called with product " + product.toString());
        productRepository.addProduct(product);
        return "redirect:/";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProduct(@PathVariable("id") int id, Model model){
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }
    @PostMapping("/editProduct/{id}")
    public String submitEditProduct(@PathVariable("id") int id, @ModelAttribute Product updatedProduct){
        Product product = productRepository.getById(id);
        if(product != null) {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setDescription(updatedProduct.getDescription());
            productRepository.update(product);
        }
        return "redirect:/products/page/1";
    }

    @GetMapping("/deleteProduct/{id}")
    public String showDeleteProductPage(@PathVariable("id") int id, Model model){
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "deleteProduct";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productRepository.delete(id);
        return "redirect:/products/page/1";
    }

    @GetMapping("/page/{pageNumber}")
    public String getProductsPage(@PathVariable("pageNumber") int pageNumber, Model model){
        int pageSize = 20;
        List<Product> products = productRepository.getProductsPage(pageNumber, pageSize);
        int totalPages = productRepository.getTotalPages(pageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("nextPage", "/products/page/" + (pageNumber + 1));
        model.addAttribute("prevPage", "/products/page/" + (pageNumber > 1 ? pageNumber - 1 : 1));
        return "page";
    }
}
