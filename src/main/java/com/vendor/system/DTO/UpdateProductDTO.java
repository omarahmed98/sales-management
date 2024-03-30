package com.vendor.system.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateProductDTO {
    private Long id;
    private String productName;
    private String description;
    private String category;
    private Long amountAvailable;
    private String cost;
}
