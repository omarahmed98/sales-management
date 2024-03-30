package com.vendor.system.Repository;

import com.vendor.system.Entity.Product;
import com.vendor.system.DTO.UpdateProductDTO;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Query to find the total number of products
    long count();

    // Query to find products with low inventory
    @Query("SELECT p FROM Product p WHERE p.amountAvailable < :threshold")
    List<Product> findProductsLowInventory(@Param("threshold") Long threshold);

    @Query("SELECT p.productName, SUM(t.quantity) AS totalSold " +
            "FROM Sale s " +
            "JOIN s.transactions t " +
            "JOIN Product p ON s.productId = p.id " +
            "GROUP BY p.productName " +
            "ORDER BY totalSold DESC")
    List<Object[]> findProductSalesPerformance();

    // Query to find pricing analysis
    @Query("SELECT p.productName, MIN(p.cost), MAX(p.cost), AVG(p.cost) " +
            "FROM Product p " +
            "GROUP BY p.productName")
    List<Object[]> findPricingAnalysis();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product p SET p.productName = :#{#data.productName} ," +
            " p.description = :#{#data.description} ," +
            " p.category = :#{#data.category} ," +
            " p.amountAvailable = :#{#data.amountAvailable} ," +
            " p.cost = :#{#data.cost} " +
            "WHERE p.id = :#{#data.id}")
    int updateProduct(@Param("data") UpdateProductDTO data);
}
