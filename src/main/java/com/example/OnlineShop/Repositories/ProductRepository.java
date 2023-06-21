package com.example.OnlineShop.Repositories;

import com.example.OnlineShop.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> getAll(){
        List<Product> query = jdbcTemplate.query("SELECT id, name, price, description FROM product",
                BeanPropertyRowMapper.newInstance(Product.class));
        return query;
    }

    public Product getById(int id){
        return jdbcTemplate.queryForObject("SELECT id, name, price, description FROM product WHERE " +
                "id = ?", BeanPropertyRowMapper.newInstance(Product.class), id);
    }

    public int addProduct(Product product){
        return jdbcTemplate
                .update("INSERT INTO product(name, price, description) VALUES(?, ?, ?)",
                        product.getName(), product.getPrice(), product.getDescription()
                );
    }
    public int update(Product product){
        return jdbcTemplate.update("UPDATE product SET name=?, price=?, description=? WHERE id=?",
                product.getName(), product.getPrice(), product.getDescription(), product.getId());
    }

    public int delete(int id){
        return jdbcTemplate.update("DELETE FROM product WHERE id=?", id);
    }

    public List<Product> getProductsPage(int pageNumber, int pageSize){
        int offset = (pageNumber - 1) * pageSize;
        return jdbcTemplate.query("SELECT id, name, price, description FROM product ORDER BY id ASC LIMIT ? OFFSET ?",
                BeanPropertyRowMapper.newInstance(Product.class), pageSize, offset);
    }

    public int getTotalPages(int pageSize){
        String query = "SELECT COUNT(*) FROM product";
        int totalProducts = jdbcTemplate.queryForObject(query, Integer.class);
        return (int)Math.ceil((double) totalProducts/pageSize);
    }
}
