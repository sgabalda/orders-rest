/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import net.sergigabol.orderrestservice.business.customers.CustomerNotFoundException;

/**
 *
 * @author gabalca
 */
@Provider
public class CustomerNotFoundExceptionMapper 
        implements ExceptionMapper<CustomerNotFoundException>{

    @Override
    public Response toResponse(CustomerNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
}
