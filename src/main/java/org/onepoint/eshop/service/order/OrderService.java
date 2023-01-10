package org.onepoint.eshop.service.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.onepoint.eshop.entity.order.AddressEntity;
import org.onepoint.eshop.entity.order.OrderEntity;
import org.onepoint.eshop.entity.order.OrderItemEntity;
import org.onepoint.eshop.entity.order.PaymentEntity;
import org.onepoint.eshop.model.order.Order;
import org.onepoint.eshop.repository.order.AddressRepository;
import org.onepoint.eshop.repository.order.OrderItemRepository;
import org.onepoint.eshop.repository.order.OrderRepository;
import org.onepoint.eshop.repository.order.PaymentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;
    @Inject
    AddressRepository addressRepository;

    OrderItemRepository orderItemRepository;

    @Inject
    PaymentRepository paymentRepository;

    public Multi<Order> findAll() {
        return orderRepository.streamAll()
                .onItem().transform(OrderService::mapToDomain);
    }

    public Uni<Order> findById(Long id) {
        return orderRepository.findById(id)
                .onItem().ifNotNull().transform(OrderService::mapToDomain)
                .onItem().ifNull().failWith(() -> new WebApplicationException("Order not found", 404));
    }

    public void create(Order order) {
        AddressEntity address = addressRepository.findById(order.getShipmentAddress().getId()).await().indefinitely();
        PaymentEntity payment = paymentRepository.findById(order.getPayment().getId()).await().indefinitely();

        Set<OrderItemEntity> orderItems = order.getOrderItems().stream()
                .map((o) -> {
                    return orderItemRepository.findById(o.getId()).await().indefinitely();
                }).collect(Collectors.toSet());
        OrderEntity entity = mapToEntity(order);
        entity.setShipmentAddress(address);
        entity.setPayment(payment);
        entity.setOrderItems(orderItems);
        Panache.withTransaction(() -> orderRepository.persist(entity)).await().indefinitely();
    }

    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> orderRepository.deleteById(id));
    }

    public static OrderEntity mapToEntity(Order order) {
        return new ObjectMapper().convertValue(order, OrderEntity.class);
    }

    public static Order mapToDomain(OrderEntity entity) {
        return new ObjectMapper().convertValue(entity, Order.class);
    }
}
