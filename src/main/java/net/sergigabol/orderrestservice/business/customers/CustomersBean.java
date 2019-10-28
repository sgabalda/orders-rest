/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import net.sergigabol.orderrestservice.domain.Customer;

/**
 *
 * @author gabalca
 */
@Singleton
public class CustomersBean implements CustomersLocal{
    
    private Map<Long,Customer> customers;
    private long idGenerator;
    
    @PostConstruct
    public void init(){
        customers = new HashMap<>();
        idGenerator = 0L;
        
    }

    @Lock(LockType.WRITE)
    @Override
    public void saveCustomer(Customer c) {
        if(c.getId()==null){
            c.setId(++idGenerator);
        }
        System.out.println("Guardant el customer "+c);
        customers.put(c.getId(), c);

    }

    @Lock(LockType.READ)
    @Override
    public Customer getCustomer(Long id) {
        System.out.println("Obtenint el customer "+id);
        return customers.get(id);
    }

    @Lock(LockType.WRITE)
    @Override
    public void deleteCustomer(Long id) {
        System.out.println("Eliminant el customer "+id);
        customers.remove(id);
    }

    @Lock(LockType.READ)
    @Override
    public List<Customer> getCustomers(int offset, int end) {
        return customers.values().stream()
                .limit(end)
                .skip(offset)  
                .collect(Collectors.toList());
    }
    
}
