package com.vendor.system.Service;

import com.vendor.system.DTO.UpdateProductDTO;
import com.vendor.system.Entity.Product;
import com.vendor.system.Repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getallProducts()
    {
        return productRepository.findAll();
    }
    public Product saveProduct(Product product) {
        return productRepository
                .save(product);         
    }
    public int updateProduct(UpdateProductDTO productDTO) {
        return productRepository
        .updateProduct(productDTO); 
    }
}
