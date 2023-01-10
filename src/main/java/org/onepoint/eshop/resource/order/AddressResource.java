package org.onepoint.eshop.resource.order;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;
import org.onepoint.eshop.model.order.Address;
import org.onepoint.eshop.service.order.AddressService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {

    @Inject
    AddressService addressService;

    @GET
    public Multi<Address> findAll() {
        return addressService.findALl();
    }

    @GET
    @Path("/{id}")
    public Uni<Address> findById(@RestPath Long id) {
        return addressService.findById(id);
    }

    @POST
    public Uni<Address> create(Address address) {
        return addressService.create(address);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return addressService.delete(id)
                .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
                        : Response.status(Response.Status.NOT_FOUND).build());
    }
}
