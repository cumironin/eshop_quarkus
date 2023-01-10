package org.onepoint.eshop.repository.order;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.onepoint.eshop.entity.order.OrderEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<OrderEntity> {
}
