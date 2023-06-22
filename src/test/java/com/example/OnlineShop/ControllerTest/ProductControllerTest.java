package com.example.OnlineShop.ControllerTest;

import com.example.OnlineShop.Controllers.ProductController;
import com.example.OnlineShop.Model.Product;
import com.example.OnlineShop.Repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testAddProduct() throws Exception{
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("prod 1");
        product1.setPrice(10.99);
        product1.setDescription("description 1");

        mockMvc.perform(MockMvcRequestBuilders.get("/products/addProduct"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("addProduct"));

        mockMvc.perform(MockMvcRequestBuilders.post("/products/addProduct")
                .flashAttr("product", product1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }
}
