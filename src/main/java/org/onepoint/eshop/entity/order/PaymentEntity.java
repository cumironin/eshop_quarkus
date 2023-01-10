package org.onepoint.eshop.entity.order;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Accessors(chain = true)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
