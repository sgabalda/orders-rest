/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest.filters;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 * Filtre que si hi ha la capçalera X-Http-Method-Override canvia el mètode de la request.
 * @author gabalca
 */

@Provider
@PreMatching        //si te @PreMatching és filtre de prematch, si no té res, es de postmatch
@Priority(Priorities.HEADER_DECORATOR)
public class MethodChangeFilter implements ContainerRequestFilter{

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        System.out.println("Al filtre de canvi de metode");
        String headerMethod = crc.getHeaderString("X-Http-Method-Override");
        if(headerMethod!=null){
            System.out.println("Al filtre de canvi de metode "+headerMethod);
            crc.setMethod(headerMethod);
        }
    }
    
}
