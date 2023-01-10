package org.onepoint.eshop.service.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.onepoint.eshop.entity.customer.CartEntity;
import org.onepoint.eshop.entity.customer.CartStatus;
import org.onepoint.eshop.entity.customer.CustomerEntity;
import org.onepoint.eshop.model.customer.Cart;
import org.onepoint.eshop.repository.customer.CartRepository;
import org.onepoint.eshop.repository.customer.CustomerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ApplicationScoped
public class CartService {

    @Inject
    CartRepository cartRepository;

    @Inject
    CustomerRepository customerRepository;
 
    public Multi<Cart> findAll()
    {
        return cartRepository.streamAll()
                .onItem().transform(CartService::mapToDomain);
    }

    public Uni<Cart> findById(Long id)
    {
        return cartRepository.findById(id)
                .onItem().ifNotNull().transform(CartService::mapToDomain);
    }

    public Uni<Cart> findByCustomerId(Long customerId)
    {
        return cartRepository.find("customer_id", customerId).firstResult()
                .onItem().ifNotNull().transform(CartService::mapToDomain)
                .onItem().ifNull().failWith(() -> new WebApplicationException("Cart not found", 404));
    }

    public void create(Long customerId)
    {
        CustomerEntity customerEntity = customerRepository.findById(customerId).await().indefinitely();
        CartEntity entity = new CartEntity()
                .setCustomer(customerEntity)
                .setStatus(CartStatus.NEW);

        Panache.withTransaction(() -> cartRepository.persistAndFlush(entity)).await().indefinitely();
    }

    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> cartRepository.deleteById(id));
    }




    public static CartEntity mapToEntity(Cart cart) {
        return new ObjectMapper().convertValue(cart, CartEntity.class);
    }

    public static Cart mapToDomain(CartEntity entity) {
        return new ObjectMapper().convertValue(entity, Cart.class);
    }
}
