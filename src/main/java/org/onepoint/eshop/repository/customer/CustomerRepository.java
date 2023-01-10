package org.onepoint.eshop.repository.customer;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.onepoint.eshop.entity.customer.CustomerEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<CustomerEntity> {
}
