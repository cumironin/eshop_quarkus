package org.onepoint.eshop.resource.customer;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;
import org.onepoint.eshop.model.customer.Customer;
import org.onepoint.eshop.service.customer.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @GET
    public Multi<Customer> findAll()
    {
        return customerService.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Customer> findById(@RestPath Long id)
    {
        return customerService.findById(id);
    }

    @POST
    public Uni<Customer> create(Customer customer)
    {
        return customerService.create(customer);
    }

    @PUT
    public Uni<Customer> update(Customer customer)
    {
        return customerService.update(customer);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        try {
            return customerService.delete(id)
                    .map(deleted -> deleted
                            ? Response.status(Response.Status.NO_CONTENT).build()
                            : Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception ex) {

            ex.printStackTrace();
            return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build());
        }
    }
}
