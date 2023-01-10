package org.onepoint.eshop.service.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.onepoint.eshop.entity.order.PaymentEntity;
import org.onepoint.eshop.model.order.Payment;
import org.onepoint.eshop.repository.order.PaymentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ApplicationScoped
public class PaymentService {


    @Inject
    PaymentRepository paymentRepository;


    public Multi<Payment> findAll()
    {
        return paymentRepository.streamAll()
                .onItem().transform(PaymentService::mapToDomain);
    }

    public Uni<Payment> findById(Long id) {
        return paymentRepository.findById(id)
                .onItem().ifNotNull().transform(PaymentService::mapToDomain)
                .onItem().ifNull().failWith(() -> new WebApplicationException("Payment not found", 404));
    }

    public Uni<Payment> create(Payment payment)
    {
        return Panache.withTransaction(() -> paymentRepository.persistAndFlush(mapToEntity(payment)))
                .onItem().transform(PaymentService::mapToDomain);
    }

    public Uni<Boolean> delete(Long id)
    {
        return Panache.withTransaction(() -> paymentRepository.deleteById(id));
    }
    public static PaymentEntity mapToEntity(Payment payment)
    {
        return new ObjectMapper().convertValue(payment, PaymentEntity.class);
    }

    public static Payment mapToDomain(PaymentEntity entity)
    {
        return  new  ObjectMapper().convertValue(entity, Payment.class);
    }

}
