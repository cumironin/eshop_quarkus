package org.onepoint.eshop.model.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Accessors(chain = true)
public class Order {
    private Long id;
    private Long cartId;
    private String status;
    private Address shipmentAddress;
    private Payment payment;
    private Set<OrderItem> orderItems;
    private BigDecimal totalPrice;
}
