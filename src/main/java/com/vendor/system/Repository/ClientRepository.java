package com.vendor.system.Repository;

import com.vendor.system.Entity.Client;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Query to find the total number of clients
    long count();

    // Query to find top-spending clients
    @Query("SELECT c, SUM(t.cost) AS totalSpent " +
    "FROM Sale s " +
    "JOIN Client c ON c.id = s.clientId " +
    "JOIN s.transactions t " +
    "GROUP BY c " +
    "ORDER BY totalSpent DESC")
    List<Object[]> findTopSpendingClients();

    // Query to find client activity
    @Query("SELECT c, COUNT(s) AS salesCount " +
            "FROM Client c " +
            "LEFT JOIN Sale s ON s.clientId = c.id " +
            "GROUP BY c")
    List<Object[]> findClientActivity();

    // Query to find client location statistics
    @Query("SELECT c.address, COUNT(c.id) AS clientCount " +
            "FROM Client c " +
            "GROUP BY c.address")
    List<Object[]> findClientLocationStatistics();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Client c SET c.name = :#{#data.name} ," +
            " c.lastName = :#{#data.lastName} ," +
            " c.mobile = :#{#data.mobile} ," +
            " c.email = :#{#data.email} ," +
            " c.address = :#{#data.address} " +
            "WHERE c.id = :#{#data.id}")
    int updateClient(@Param("data") Client data);
}
