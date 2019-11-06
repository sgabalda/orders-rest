/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.io.InputStream;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import net.sergigabol.orderrestservice.business.customers.CustomersSearchCriteria;

/**
 *
 * @author gabalca
 */
@Path("/customers")
public interface CustomersResourceInterface {
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createCustomer(InputStream is);
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StreamingOutput getAllCustomers(
            @BeanParam CustomersSearchCriteria csc);

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") long custId);
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateCustomer(InputStream is, @PathParam("id") long id);
   
    
    @Path("/{id: \\d+}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StreamingOutput getCustomer(@PathParam("id") long custId);
    
    @Path("/{first}-{last}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StreamingOutput getCustomerByFullName(
            @PathParam("first") String first,
            @PathParam("last") String last);
    
    //fara match si la URI es /customers/hola/altre
    //@Path("/{nif: \\d{8}[A-Z]{1}}")
    @Path("/{nif: .+}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StreamingOutput getCustomerByNif(
            @PathParam("nif") String nif);
    
    
    @Path("/{id}/orders")
    public Object getOrdersSubresource(@PathParam("id") long customerId);
    
}
