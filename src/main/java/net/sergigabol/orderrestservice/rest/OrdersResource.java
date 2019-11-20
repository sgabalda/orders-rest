/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.sergigabol.orderrestservice.domain.LineItem;
import net.sergigabol.orderrestservice.domain.Order;

/**
 *
 * @author gabalca
 */
@Path("/orders")
public interface OrdersResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //Amb el matrixParam no funciona?
    public Response getAllOrders(@QueryParam("excludecanceled") String excludeCanceled);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newOrder(Order o);
    
    @Path("/{id}/lineitems")
    @GET
    public List<LineItem> getOrderLineItems(@PathParam("id") long orderId);
    
    @Path("/{id}")
    @GET
    public Response getOrder(@PathParam("id") long orderId);
    
    @Path("/{id}")
    @PUT
    public Response saveOrder(Order order);
    
    
    @Path("/{id}/process")
    @POST
    public void processOrder(@PathParam("id") long orderId,
            @Suspended AsyncResponse response);
    
    @LOCK
    public void exempleLock();

}
