/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.orders;

import java.util.List;
import javax.ejb.Local;
import net.sergigabol.orderrestservice.domain.Order;

/**
 *
 * @author gabalca
 */
@Local
public interface OrdersLocal {
    
    void saveOrder(Order o);
    void deleteOrder(Long orderId);
    Order getOrderById(Long orderId);
    List<Order> getAllOrders(int offset, int end,boolean excludeCanceled);
    List<Order> getOrdersByCustomer(Long customerId,int offset, int end,boolean excludeCanceled);
}
