/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
    public List<Order> getAllOrders(String excludeCanceled) {
        System.out.println("getting orders with excl:"+excludeCanceled);
        boolean excludeCanceledBool = Boolean.valueOf(excludeCanceled);
        if(customerId!=null){
            return ordersBean.getOrdersByCustomer(customerId, 0, 10,excludeCanceledBool);
        }
        return ordersBean.getAllOrders(0, 10,excludeCanceledBool);
    }

    @Override
    public void newOrder(Order o) {
        ordersBean.saveOrder(o);
    }

    @Override
    public void exempleLock() {
        
    }

    @Override
    public List<LineItem> getOrderLineItems(long orderId) {
        //TODO implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
