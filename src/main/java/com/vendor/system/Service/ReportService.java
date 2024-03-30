package com.vendor.system.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.vendor.system.Entity.Transaction;
import com.vendor.system.Entity.Product;
import com.vendor.system.Entity.Sale;
import com.vendor.system.Entity.User;
import com.vendor.system.Entity.Client;
import com.vendor.system.Repository.SaleRepository;
import com.vendor.system.Repository.TransactionRepository;
import com.vendor.system.Repository.ClientRepository;
import com.vendor.system.Repository.ProductRepository;

import jakarta.transaction.Transactional;

import com.vendor.system.Config.ResourceNotFoundException;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReportService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    //sale reports
  public List<Sale> getSalesByDateRange(Date startDate, Date endDate) {
        return saleRepository.findByCreationDateBetween(startDate, endDate);
    }

    public BigDecimal getTotalRevenueByDateRange(Date startDate, Date endDate) {
        return saleRepository.calculateTotalRevenue(startDate, endDate);
    }

    public List<Object[]> getTopSellingProductsByDateRange(Date startDate, Date endDate) {
        return saleRepository.findTopSellingProducts(startDate, endDate);
    }

    public List<Object[]> getTopPerformingSellersByDateRange(Date startDate, Date endDate) {
        return saleRepository.findTopPerformingSellers(startDate, endDate);
    }

    //client reports
    public long getTotalNumberOfClients() {
        return clientRepository.count();
    }

    public List<Object[]> getTopSpendingClients() {
        return clientRepository.findTopSpendingClients();
    }

    public List<Object[]> getClientActivity() {
        return clientRepository.findClientActivity();
    }

    public List<Object[]> getClientLocationStatistics() {
        return clientRepository.findClientLocationStatistics();
    }

    //product reports
    public long getTotalNumberOfProducts() {
        return productRepository.count();
    }

    public List<Product> getProductsLowInventory(Long threshold) {
        return productRepository.findProductsLowInventory(threshold);
    }

    public List<Object[]> getProductSalesPerformance() {
        return productRepository.findProductSalesPerformance();
    }

    public List<Object[]> getPricingAnalysis() {
        return productRepository.findPricingAnalysis();
        
    }

}
