/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author gabalca
 */
//@Provider ho comentem perquè ho asignarem ab DynamicFeature
public class CacheControlFilter implements ContainerResponseFilter {
    
    private int maxAge;

    public CacheControlFilter(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public void filter(ContainerRequestContext crc, ContainerResponseContext crc1) 
            throws IOException {
        System.out.println("al de response");
        if(crc.getMethod().equals("GET")){
            CacheControl cc = new CacheControl();
            cc.setMaxAge(maxAge);
            crc1.getHeaders().add("Cache-Control", cc);
        }
    }
    
}
