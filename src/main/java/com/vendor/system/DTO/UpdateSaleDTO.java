package com.vendor.system.DTO;

import java.util.List;

import com.vendor.system.Entity.Sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateSaleDTO {
    private Sale sale;
    private List<Long> productIds;
}
