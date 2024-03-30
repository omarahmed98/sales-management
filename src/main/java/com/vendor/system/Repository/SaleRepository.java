package com.vendor.system.Repository;

import com.vendor.system.Entity.Sale;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.creationDate BETWEEN :startDate AND :endDate")
    List<Sale> findByCreationDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.creationDate BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalRevenue(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT p.productName, SUM(t.quantity) AS totalQuantity " +
            "FROM Sale s " +
            "JOIN s.transactions t " +
            "JOIN Product p ON s.productId = p.id " +
            "WHERE s.creationDate BETWEEN :startDate AND :endDate " +
            "GROUP BY p.productName " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT u.username, COUNT(s.id) AS salesCount " +
            "FROM Sale s " +
            "JOIN User u ON s.sellerId = u.id " +
            "WHERE s.creationDate BETWEEN :startDate AND :endDate " +
            "GROUP BY u.username " +
            "ORDER BY salesCount DESC")
    List<Object[]> findTopPerformingSellers(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
