/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author gabalca
 */
@Provider //es un filtre de post-match
@ApplyLogFilter
public class LogRequestFilter implements ContainerRequestFilter{

    private static final Logger LOG = Logger.getLogger(LogRequestFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        LOG.log(Level.INFO, "Request to {0} with {1}", 
                new Object[]{crc.getUriInfo().getAbsolutePath(), crc.getMethod()});
        boolean toAbort = false;
        if(toAbort){
            crc.abortWith(Response.ok().build());
            //throw new NotAuthorizedException("Not valid auth");
        }
    }
    
}
