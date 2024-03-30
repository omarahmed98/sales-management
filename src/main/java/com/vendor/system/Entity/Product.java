package com.vendor.system.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Product name cannot be empty")
    @Column(name = "product_name")
    private String productName;

    @NotEmpty(message = "Description cannot be empty")
    @Column(name = "description")
    private String description;

    @NotEmpty(message = "Category cannot be empty")
    @Column(name = "category")
    private String category;

    @NotNull(message = "Amount available cannot be null")
    @Column(name = "amount_available")
    private Long amountAvailable;

    @NotNull(message = "Creation date cannot be empty")
    @Column(name = "creation_date")
    private Date creationDate;

    @NotNull(message = "Cost cannot be empty")
    @Column(name = "cost")
    private Long cost;

}

