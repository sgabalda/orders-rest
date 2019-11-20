/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest.filters;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author gabalca
 */
@Provider
public class MaxAgeDynamicFeature implements DynamicFeature{

    @Override
    public void configure(ResourceInfo ri, FeatureContext fc) {
        MaxAge ma = ri.getResourceMethod().getAnnotation(MaxAge.class);
        if(ma!=null){
            CacheControlFilter ccf = new CacheControlFilter(ma.value());
            fc.register(ccf);
        }
    }
    
}
