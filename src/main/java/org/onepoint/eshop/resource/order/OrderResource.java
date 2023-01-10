package org.onepoint.eshop.resource.order;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;
import org.onepoint.eshop.model.order.Order;
import org.onepoint.eshop.service.order.OrderService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;


    @GET
    public Multi<Order> findAll()
    {
        return orderService.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Order> findById(@RestPath Long id)
    {
        return orderService.findById(id);
    }


    public Response create(Order order)
    {
        orderService.create(order);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return orderService.delete(id)
                .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
                        : Response.status(Response.Status.NOT_FOUND).build());
    }


}
