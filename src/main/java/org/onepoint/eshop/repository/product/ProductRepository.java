package org.onepoint.eshop.repository.product;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.onepoint.eshop.entity.product.ProductEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductEntity> {
}
