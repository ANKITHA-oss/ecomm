package com.mtd.ecom_server.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mtd.ecom_server.models.Product;
import com.mtd.ecom_server.repos.ProductRepo;
@CrossOrigin()
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    // ✅ Get all products
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // ✅ Add a new product
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product newProduct) {
        return productRepo.save(newProduct);
    }

    // ✅ Delete a product by ID
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        Optional<Product> findProduct = productRepo.findById(id);
        if (findProduct.isPresent()) {
            productRepo.deleteById(id);
            return "Product deleted: " + findProduct.get().getName();
        } else {
            return "Failed to delete product. Product not found.";
        }
    }

    // ✅ Edit an existing product
    @PutMapping("/edit/{id}")
    public Product editProduct(@PathVariable String id, @RequestBody Product newProduct) {
        return productRepo.findById(id).map(product -> {
            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setCategory(newProduct.getCategory());
            product.setTags(newProduct.getTags());
            product.setPrice(newProduct.getPrice());
            product.setStock(newProduct.getStock());
            return productRepo.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
