package com.vendor.system.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vendor.system.DTO.ApiResponse;
import com.vendor.system.Entity.Client;
import com.vendor.system.Entity.Product;
import com.vendor.system.Entity.Sale;
import com.vendor.system.Repository.ClientRepository;
import com.vendor.system.Service.ClientService;
import com.vendor.system.Service.ReportService;
import com.vendor.system.Service.SaleService;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("api/v1/reports")
@Slf4j
public class ReportsController {

    private final ReportService reportService;

    @Operation(summary = "get sale by range date")
    @GetMapping("/sale/all")
    public ResponseEntity<?> getAllsales(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<Sale> sals = reportService.getSalesByDateRange(startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(true, sals, "Report Retreived Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "get total revenue by range date")
    @GetMapping("/sale/revenue")
    public ResponseEntity<?> getTotalRevenue(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            BigDecimal sals = reportService.getTotalRevenueByDateRange(startDate, endDate);
            if(sals == null)
            {
                return new ResponseEntity<>(new ApiResponse<>(false, 0, "Np top selling in this range"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>(true, sals, "Report Retreived Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "get Top Selling Products By DateRange")
    @GetMapping("/sale/top-selling")
    public ResponseEntity<?> getTopSellingProductsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<Object[]> sals = reportService.getTopSellingProductsByDateRange(startDate, endDate);
            if(sals.isEmpty())
            {
                return new ResponseEntity<>(new ApiResponse<>(false, 0, "Np top selling in this range"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>(true, sals, "Report Retreived Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "get Top Performing Products ByDateRange")
    @GetMapping("/sale/top-performer")
    public ResponseEntity<?> getTopPerformingSellersByDateRange(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<Object[]> sals = reportService.getTopPerformingSellersByDateRange(startDate, endDate);
            if(sals.isEmpty())
            {
                return new ResponseEntity<>(new ApiResponse<>(false, 0, "Np top perfoming in this range"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>(true, sals, "Report Retreived Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //client reports
    @Operation(summary = "get total number of clients")
    @GetMapping("/client/count")
    public ResponseEntity<?> getTotalNumberOfClients() {
        try {
            long client = reportService.getTotalNumberOfClients();
            return new ResponseEntity<>(new ApiResponse<>(true, client, "Report Retreived Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

        @Operation(summary = "get Top Spending clients")
        @GetMapping("/client/top-spending")
        public ResponseEntity<?> getTopSpendingClients() {
            try {
                List<Object[]> client = reportService.getTopSpendingClients();
                return new ResponseEntity<>(new ApiResponse<>(true, client, "Report Retreived Successfully"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
    
        }
        @Operation(summary = "get client activity")
        @GetMapping("/client/activity")
        public ResponseEntity<?> getClientActivity() {
            try {
                List<Object[]> client = reportService.getClientActivity();
                return new ResponseEntity<>(new ApiResponse<>(true, client, "Report Retreived Successfully"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
    
        }
        @Operation(summary = "get client locations")
        @GetMapping("/client/location")
        public ResponseEntity<?> getClientLocationStatistics() {
            try {
                List<Object[]> client = reportService.getClientLocationStatistics();
                return new ResponseEntity<>(new ApiResponse<>(true, client, "Report Retreived Successfully"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
    
        }


         //Product reports
    @Operation(summary = "getTotalNumberOfProducts")
    @GetMapping("/product/count")
    public ResponseEntity<?> getTotalNumberOfProducts() {
        try {
            long product = reportService.getTotalNumberOfProducts();
            return new ResponseEntity<>(new ApiResponse<>(true, product, "Report Retreived Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

        @Operation(summary = "get Products LowInventory")
        @GetMapping("/product/low-inventory")
        public ResponseEntity<?> getProductsLowInventory(@RequestParam Long threshold) {
            try {
                List<Product> product = reportService.getProductsLowInventory(threshold);
                return new ResponseEntity<>(new ApiResponse<>(true, product, "Report Retreived Successfully"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
    
        }
        @Operation(summary = "get products performance")
        @GetMapping("/product/performance")
        public ResponseEntity<?> getProductSalesPerformance() {
            try {
                List<Object[]> product = reportService.getProductSalesPerformance();
                return new ResponseEntity<>(new ApiResponse<>(true, product, "Report Retreived Successfully"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
    
        }
        @Operation(summary = "get Pricing Analysis")
        @GetMapping("/product/analysis")
        public ResponseEntity<?> getPricingAnalysis() {
            try {
                List<Object[]> product = reportService.getPricingAnalysis();
                return new ResponseEntity<>(new ApiResponse<>(true, product, "Report Retreived Successfully"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal server error"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
    
        }

}
