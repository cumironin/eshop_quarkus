package org.onepoint.eshop.resource.product;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;
import org.onepoint.eshop.model.product.Category;
import org.onepoint.eshop.service.product.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    @Inject
    CategoryService categoryService;

    @GET
    public Multi<Category> findAll()
    {
        return categoryService.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Category> findById(@RestPath Long id)
    {
        return categoryService.findById(id);
    }

    @POST
    public Uni<Category> create(Category category)
    {
        return categoryService.create(category);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@RestPath Long id)
    {
        return categoryService.delete(id)
                .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
                    : Response.status(Response.Status.NOT_FOUND).build());
    }
}
