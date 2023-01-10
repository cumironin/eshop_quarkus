package org.onepoint.eshop.resource.customer;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;
import org.onepoint.eshop.model.customer.Cart;
import org.onepoint.eshop.model.customer.Customer;
import org.onepoint.eshop.service.customer.CartService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    @Inject
    CartService cartService;

    @GET
    public Multi<Cart> findAll()
    {
        return cartService.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Cart> findById(@RestPath Long id) {
        return cartService.findById(id);
    }

    @GET
    @Path("/customer/{id}")
    public Uni<Cart> findByCustomerId(@RestPath Long id) {
        return cartService.findByCustomerId(id);
    }

    @POST
    public Response create(Customer customer) {
        cartService.create(customer.getId());
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return cartService.delete(id)
                .map(deleted -> deleted
                        ? Response.status(Response.Status.NO_CONTENT).build()
                        : Response.status(Response.Status.NOT_FOUND).build());
    }


}
