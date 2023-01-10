package org.onepoint.eshop.resource.order;


import io.smallrye.mutiny.Multi;
import org.onepoint.eshop.model.order.OrderItem;
import org.onepoint.eshop.service.order.OrderItemService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/order_items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderItemResource {


    @Inject
    OrderItemService orderItemService;

    @GET
    public Multi<OrderItem> findAll() {
        return orderItemService.findAll();
    }

}
