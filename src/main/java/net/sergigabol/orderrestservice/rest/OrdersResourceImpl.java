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
import net.sergigabol.orderrestservice.domain.Order;

/**
 *
 * @author gabalca
 */
@RequestScoped
public class OrdersResourceImpl implements OrdersResource{

    @EJB
    OrdersLocal ordersBean;
    
    @Override
    public List<Order> getAllOrders() {
        return ordersBean.getAllOrders(0, 10);
    }

    @Override
    public void newOrder(Order o) {
        ordersBean.saveOrder(o);
    }

    @Override
    public void exempleLock() {
        
    }
    
}
