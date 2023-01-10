package org.onepoint.eshop.entity.product;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CategoryEntity category;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;

}
