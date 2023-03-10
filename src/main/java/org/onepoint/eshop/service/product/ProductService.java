package org.onepoint.eshop.service.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.onepoint.eshop.entity.product.CategoryEntity;
import org.onepoint.eshop.entity.product.ProductEntity;
import org.onepoint.eshop.model.product.Product;
import org.onepoint.eshop.repository.product.CategoryRepository;
import org.onepoint.eshop.repository.product.ProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    CategoryRepository categoryRepository;

    public Multi<Product> findAll()
    {
        return productRepository.streamAll()
                .onItem().transform(ProductService::mapToDomain);
    }

    public Uni<Product> findById(Long id)
    {
        return productRepository.findById(id)
                .onItem().ifNotNull().transform(ProductService::mapToDomain)
                .onItem().ifNull().failWith(() -> new WebApplicationException("Product not found",  404));
    }

    public void create(Product product)
    {
        CategoryEntity categoryEntity = categoryRepository.findById(product.getCategory().getId()).await().indefinitely();
        ProductEntity entity = mapToEntity(product);
        entity.setCategory(categoryEntity);

        Panache.withTransaction(() -> productRepository.persist(entity)).await().indefinitely();
    }

    public Uni<Boolean> delete(Long id)
    {
        return Panache.withTransaction(() -> productRepository.deleteById(id));
    }

    public static ProductEntity mapToEntity(Product product) {
        return new ObjectMapper().convertValue(product, ProductEntity.class);
    }

    public static Product mapToDomain(ProductEntity entity)
    {
        return new ObjectMapper().convertValue(entity, Product.class);
    }
}
