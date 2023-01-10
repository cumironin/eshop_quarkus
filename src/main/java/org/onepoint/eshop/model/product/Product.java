package org.onepoint.eshop.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class Product {

    private Long id;
    private Category category;
    private String status;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
