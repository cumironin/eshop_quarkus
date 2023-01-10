package org.onepoint.eshop.repository.customer;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.onepoint.eshop.entity.customer.CartEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CartRepository implements PanacheRepository<CartEntity> {
}
