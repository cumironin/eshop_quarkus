package org.onepoint.eshop.entity.order;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Payment {

    private Long id;
    private String status;
    private String method;
}
