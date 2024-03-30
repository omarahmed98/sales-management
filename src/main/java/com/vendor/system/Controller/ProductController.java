package com.vendor.system.Controller;

import com.vendor.system.DTO.ApiResponse;
import com.vendor.system.DTO.UpdateProductDTO;
import com.vendor.system.Entity.Product;
import com.vendor.system.Repository.ProductRepository;
import com.vendor.system.Service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("api/v1/product")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @Operation(summary = "get all products")
    @GetMapping("")
    public ResponseEntity<?> getProducts() {
        List<Product> products = productService.getallProducts();
        return new ResponseEntity<>(new ApiResponse<>(true,products,"Products Retreived Successfully"),HttpStatus.OK);
    }

    @Operation(summary = "get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null)
        {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Product Not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>(true,product,"Product Retreived Successfully"),HttpStatus.OK);
    }

    @Operation(summary = "add product to list of products")
    @PostMapping("")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return new ResponseEntity<>(new ApiResponse<>(false,errors,"Bad Request" ), HttpStatus.BAD_REQUEST);
            }  
            Product savedProduct = productService.saveProduct(product);
            return new ResponseEntity<>(new ApiResponse<>(true,savedProduct.getId(),"Product Added Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update data of product")
    @PutMapping("")
    public ResponseEntity<?> updateProductData(@Valid @RequestBody UpdateProductDTO product, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return new ResponseEntity<>(new ApiResponse<>(false,errors,"Bad Request" ), HttpStatus.BAD_REQUEST);
                }  
            int savedProduct = productService.updateProduct(product);
            if(savedProduct==0)
            {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"No rows affected"),HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>(true,product.getId(),"Product Updated Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "delete the product")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        try {
            Product productSeller = productRepository.findById(id).orElse(null);
            if(productSeller == null)
            {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"Wrong Product Id"),HttpStatus.NOT_FOUND);
            }
            productRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse<>(true,id,"Product delted Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
