package org.onepoint.eshop.repository.product;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.onepoint.eshop.entity.product.CategoryEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<CategoryEntity> {
}
