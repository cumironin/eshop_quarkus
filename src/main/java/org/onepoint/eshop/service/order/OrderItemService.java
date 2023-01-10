package org.onepoint.eshop.service.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.onepoint.eshop.entity.order.OrderItemEntity;
import org.onepoint.eshop.model.order.OrderItem;
import org.onepoint.eshop.repository.order.OrderItemRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ApplicationScoped
public class OrderItemService {

    @Inject
    OrderItemRepository orderItemRepository;

    public Multi<OrderItem> findAll() {
        return orderItemRepository.streamAll().onItem().transform(OrderItemService::mapToDomain);
    }

    public Uni<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id)
                .onItem().ifNotNull().transform(OrderItemService::mapToDomain)
                .onItem().ifNull().failWith(() -> new WebApplicationException("OrderItem not found", 404));
    }

    public Uni<OrderItem> create(OrderItem orderItem) {
        return Panache.withTransaction(() -> orderItemRepository.persistAndFlush(mapToEntity(orderItem)))
                .onItem().transform(OrderItemService::mapToDomain);
    }

    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> orderItemRepository.deleteById(id));
    }

    public static OrderItemEntity mapToEntity(OrderItem orderItem) {
        return new ObjectMapper().convertValue(orderItem, OrderItemEntity.class);
    }

    public static OrderItem mapToDomain(OrderItemEntity entity) {
        return new ObjectMapper().convertValue(entity, OrderItem.class);
    }
}
