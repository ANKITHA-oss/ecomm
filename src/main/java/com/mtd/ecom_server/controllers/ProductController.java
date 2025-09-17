package com.mtd.ecom_server.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mtd.ecom_server.EcomServerApplication;
import com.mtd.ecom_server.models.Product;
import com.mtd.ecom_server.repos.ProductRepo;

@CrossOrigin()
@RestController
@RequestMapping("/products")
public class ProductController {

    private final EcomServerApplication ecomServerApplication;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepo productRepo;

    ProductController(EcomServerApplication ecomServerApplication) {
        this.ecomServerApplication = ecomServerApplication;
    }

    // ✅ Get all products
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepo.findAll();
    }

    // ✅ Add a new product
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product newProduct) {
        log.info("Adding product: {}", newProduct);
        return productRepo.save(newProduct);
    }

    // ✅ Delete a product by ID
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        Optional<Product> findProduct = productRepo.findById(id);
        if (findProduct.isEmpty()) {
            productRepo.deleteById(id);
            log.info("Deleted product: {}", findProduct.get().getName());
            return "Product deleted: " + findProduct.get().getName();
        } else {
            log.warn("Failed to delete product. Product not found with id: {}", id);
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
            log.info("Updating product with id: {}", id);
            return productRepo.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
