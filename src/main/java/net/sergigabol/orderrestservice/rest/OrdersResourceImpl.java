/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import net.sergigabol.orderrestservice.business.orders.OrdersLocal;
import net.sergigabol.orderrestservice.domain.LineItem;
import net.sergigabol.orderrestservice.domain.Order;

/**
 *
 * @author gabalca
 */
@RequestScoped
public class OrdersResourceImpl implements OrdersResource{

    @EJB
    OrdersLocal ordersBean;
    
    private Long customerId;

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    

    @Override
    public Response getAllOrders(String excludeCanceled) {
        System.out.println("getting orders with excl:"+excludeCanceled);
        boolean excludeCanceledBool = Boolean.valueOf(excludeCanceled);
        if(customerId!=null){
            return Response
                    .ok(ordersBean.getOrdersByCustomer(customerId, 0, 10,excludeCanceledBool))
                    .build();
        }
        return Response
                    .ok(ordersBean.getAllOrders(0, 10,excludeCanceledBool))
                .build();
    }

    @Override
    public Response newOrder(Order o) {
        ordersBean.saveOrder(o);
        return Response
                .created(UriBuilder.fromPath("/orders/{id}").build(o.getId()))
                .build();
    }

    @Override
    public void exempleLock() {
        
    }

    @Override
    public List<LineItem> getOrderLineItems(long orderId) {
        //TODO implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Context
    Request request;
    
    @Override
    public Response getOrder(long orderId) {
        Order o = ordersBean.getOrderById(orderId);
        if(o == null) throw new NotFoundException();
        
        EntityTag etag = new EntityTag(String.valueOf(o.hashCode()));
        
        //analitza la request per si hi ha un If-Modified-Since, si no hi ha el header
        //o és anterior a la data que li passem, retorna null
        //si hi és i la data que li passem és igual, retorna un ResponseBuilder amb
        //304 
        Response.ResponseBuilder preconditions 
                = request.evaluatePreconditions(o.getDate(),etag);
        
        CacheControl cc = new CacheControl();
        cc.setMaxAge(10);
        cc.setMustRevalidate(true);
        
        if(preconditions !=null){
            return preconditions
                    .lastModified(o.getDate())
                    .cacheControl(cc)
                    .tag(etag)
                    .build();
        }
                
        return Response.ok(o)
                .lastModified(o.getDate())
                .cacheControl(cc)
                .tag(etag)
                .build();
    }
    
}
