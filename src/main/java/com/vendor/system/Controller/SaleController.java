package com.vendor.system.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.vendor.system.DTO.ApiResponse;
import com.vendor.system.Entity.Sale;
import com.vendor.system.Repository.SaleRepository;
import com.vendor.system.Service.SaleService;


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("api/v1/sale")
@Slf4j
public class SaleController {

    private final SaleService saleService;
    private final SaleRepository saleRepository;

    @Operation(summary = "get all sales")
    @GetMapping("")
    public ResponseEntity<?> getSales() {
        List<Sale> sales = saleService.getallSales();
        return new ResponseEntity<>(new ApiResponse<>(true,sales,"Sales Retreived Successfully"),HttpStatus.OK);
    }

    @Operation(summary = "get sale by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleDetails(@PathVariable("id") Long id) {
        Sale sale = saleRepository.findById(id).orElse(null);
        if(sale == null)
        {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Sale Not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>(true,sale,"Sale Retreived Successfully"),HttpStatus.OK);
    }

    @Operation(summary = "add sale to list of sales")
    @PostMapping("")
    public ResponseEntity<?> addSale(@Valid @RequestBody Sale SaleDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return new ResponseEntity<>(new ApiResponse<>(false,errors,"Bad Request" ), HttpStatus.BAD_REQUEST);
            }            
            Sale savedSale = saleService.createSale(SaleDTO);
            return new ResponseEntity<>(new ApiResponse<>(true,savedSale.getId(),"Sale Added Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update data of sale")
    @PutMapping("")
    public ResponseEntity<?> updateSaleData(@Valid @RequestBody Sale updateSaleDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return new ResponseEntity<>(new ApiResponse<>(false,errors,"Bad Request" ), HttpStatus.BAD_REQUEST);
            }            
            Sale savedSale = saleService.updateSale(updateSaleDTO);
            if(savedSale==null)
            {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"No rows affected"),HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>(true,updateSaleDTO.getId(),"Sale Updated Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "delete the sale")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable("id") Long id){
        try {
            Sale saleSeller = saleRepository.findById(id).orElse(null);
            if(saleSeller == null)
            {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"Wrong Sale Id"),HttpStatus.NOT_FOUND);
            }
            saleRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse<>(true,id,"Sale delted Successfully"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
   
}
