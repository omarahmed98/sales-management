package com.vendor.system.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Service;
import com.vendor.system.Entity.Transaction;
import com.vendor.system.Entity.Sale;
import com.vendor.system.Repository.SaleRepository;
import com.vendor.system.Repository.TransactionRepository;

import jakarta.transaction.Transactional;

import com.vendor.system.Config.ResourceNotFoundException;


@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService {
    private final SaleRepository saleRepository;
    private final TransactionRepository transactionRepository;

    public List<Sale> getallSales() {
        return saleRepository.findAll();
    }

    @Transactional
    public Sale createSale(Sale sale) {
        Sale savedSale = saleRepository.save(sale);

        // Set saleId for each Transaction and associate it with the Sale
        for (Transaction transaction : sale.getTransactions()) {
            transaction.setSale(savedSale); // Associate Transaction with Sale
        }

        // Save the Transactions
        transactionRepository.saveAll(sale.getTransactions());

        return savedSale;
    }

    @Transactional
    public Sale updateSale(Sale updatedSale) {
        Long saleId = updatedSale.getId();
        Sale existingSale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + saleId));

        // Update fields of the existing Sale with the values from updatedSale
        existingSale.setCreationDate(updatedSale.getCreationDate());
        existingSale.setClientId(updatedSale.getClientId());
        existingSale.setSellerId(updatedSale.getSellerId());
        existingSale.setProductId(updatedSale.getProductId());

        existingSale.setTotal(updatedSale.getTotal());

        // Update associated Transactions
        List<Transaction> updatedTransactions = updatedSale.getTransactions();
        for (Transaction updatedTransaction : updatedTransactions) {
            // If Transaction already exists, update it; otherwise, create a new one
            if (updatedTransaction.getId() != null) {
                Transaction existingTransaction = transactionRepository.findById(updatedTransaction.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Transaction not found with id: " + updatedTransaction.getId()));
                existingTransaction.setQuantity(updatedTransaction.getQuantity());
                existingTransaction.setCost(updatedTransaction.getCost());
                transactionRepository.save(existingTransaction);
            } else {
                updatedTransaction.setSale(existingSale); // Associate Transaction with Sale
                transactionRepository.save(updatedTransaction);
            }
        }

        // Save the updated Sale
        return saleRepository.save(existingSale);
    }
}
