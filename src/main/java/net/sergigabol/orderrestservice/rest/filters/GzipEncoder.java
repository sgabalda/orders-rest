/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest.filters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 *
 * @author gabalca
 */
@Provider
public class GzipEncoder implements WriterInterceptor{

    @Override
    public void aroundWriteTo(WriterInterceptorContext wic) 
            throws IOException, WebApplicationException {
        
        OutputStream out =wic.getOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(out);
        
        wic.getHeaders().putSingle("Content-Encoding", "gzip");
        wic.setOutputStream(gos);
        wic.proceed();
        
    }
    
}
