package org.onepoint.eshop.service.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.onepoint.eshop.entity.product.CategoryEntity;
import org.onepoint.eshop.model.product.Category;
import org.onepoint.eshop.repository.product.CategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;


    public Multi<Category> findAll()
    {
        return categoryRepository.streamAll()
                .onItem().transform(CategoryService::mapToDomain);
    }

    public Uni<Category> findById(Long id)
    {
        return categoryRepository.findById(id)
                .onItem().ifNotNull().transform(CategoryService::mapToDomain)
                .onItem().ifNull().failWith(() -> new WebApplicationException("Category Not Found", 404));
    }

    public Uni<Category> create(Category category)
    {
        return Panache.withTransaction(() -> categoryRepository.persistAndFlush(mapToEntity(category)))
                .onItem().transform(CategoryService::mapToDomain);
    }

    public Uni<Boolean> delete(Long id)
    {
        return Panache.withTransaction(() -> categoryRepository.deleteById(id));
    }

    public static Category mapToDomain(CategoryEntity entity)
    {
        return new ObjectMapper().convertValue(entity, Category.class);
    }

    public static CategoryEntity mapToEntity(Category category)
    {
        return new ObjectMapper().convertValue(category, CategoryEntity.class);
    }

}
