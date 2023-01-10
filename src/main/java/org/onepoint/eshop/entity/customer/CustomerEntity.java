package org.onepoint.eshop.entity.customer;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Entity
@Table(name = "customers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
}
