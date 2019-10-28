/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import net.sergigabol.orderrestservice.domain.Order;

/**
 *
 * @author gabalca
 */
@Singleton
public class OrdersBean implements OrdersLocal{
    
    private Map<Long, Order> orders;
    private long idGenerator;
    
    @PostConstruct
    public void init(){
        orders = new HashMap<>();
        idGenerator = 0L;
    }

    @Lock(LockType.WRITE)
    @Override
    public void saveOrder(Order o) {
        if(o.getId()==null){
            o.setId(++idGenerator);
        }
        orders.put(o.getId(), o);
    }

    @Lock(LockType.WRITE)
    @Override
    public void deleteOrder(Long orderId) {
        orders.remove(orderId);
    }

    @Lock(LockType.READ)
    @Override
    public Order getOrderById(Long orderId) {
        return orders.get(orderId);
    }

    @Lock(LockType.READ)
    @Override
    public List<Order> getAllOrders(int offset, int end) {
        return orders.values().stream()
                .limit(end)
                .skip(offset)  
                .collect(Collectors.toList());
    }

    @Lock(LockType.READ)
    @Override
    public List<Order> getOrdersByCustomer(Long customerId, int offset, int end) {
        
        return orders.values().stream()
                .filter(o -> o.getCustomer().getId()==customerId)
                .limit(end)
                .skip(offset)  
                .collect(Collectors.toList());
        
    }
    
    
    
    
}
