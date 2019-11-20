/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.sergigabol.orderrestservice.domain.Product;

/**
 *
 * @author gabalca
 */
@Path("/products")
public interface ProductResource {
    
    @GET
    public List<Product> getAllProducts(
            @DefaultValue("0") @QueryParam("start") int start, 
            @DefaultValue("50") @QueryParam("end")  int end, 
            @QueryParam("orderby") List<String> orderby);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createNewProduct(Product p);

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getProduct(@PathParam("id") long productId);
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProductAsText(@PathParam("id") long productId);
    
    @PUT
    @Path("/{id}/image")
    @Consumes({"image/jpeg","image/png","image/gif"})
    public void saveProductImage(InputStream is,
            @PathParam("id") long productId);
        
    
    @GET
    @Path("/{id}/image")
    @Produces({"image/jpeg","image/png","image/gif"})
    public File saveProductImage(@PathParam("id") long productId);
    
}
