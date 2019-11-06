/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.orders;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import net.sergigabol.orderrestservice.business.customers.CustomersLocal;
import net.sergigabol.orderrestservice.domain.Order;

/**
 *
 * @author gabalca
 */
@Singleton
public class OrdersBean implements OrdersLocal{
    
    @EJB
    private CustomersLocal customersBean;
    
    private Map<Long, Order> orders;
    private long idGenerator;
    
    @PostConstruct
    public void init(){
        orders = new HashMap<>();
        idGenerator = 0L;
        
        for(int i=1; i<11; i++){
            Order o = new Order();
            o.setDate(new Date());
            o.setCustomer(customersBean.getCustomer((long)i));
            o.setCanceled(i%3==0);
            saveOrder(o);
        }
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
    public List<Order> getAllOrders(int offset, int end,boolean excludeCanceled) {
        return orders.values().stream()
                //si excludeCancel es false, passen totes, si es true, les que no estan cancelades
                .filter(o -> excludeCanceled?!o.isCanceled():true)  
                .limit(end)
                .skip(offset)  
                .collect(Collectors.toList());
    }

    @Lock(LockType.READ)
    @Override
    public List<Order> getOrdersByCustomer(Long customerId, int offset, int end,boolean excludeCanceled) {
        
        return orders.values().stream()
                .filter(o -> o.getCustomer().getId().equals(customerId))
                //si excludeCancel es false, passen totes, si es true, les que no estan cancelades
                .filter(o -> excludeCanceled?!o.isCanceled():true)  
                .limit(end)
                .skip(offset)  
                .collect(Collectors.toList());
        
    }
    
    
    
    
}
