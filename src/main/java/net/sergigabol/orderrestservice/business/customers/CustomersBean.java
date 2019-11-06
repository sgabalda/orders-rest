/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.customers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
public class CustomersBean implements CustomersLocal {
    
    private Map<Long, Customer> customers;
    private long idGenerator;
    
    @PostConstruct
    public void init() {
        customers = new HashMap<>();
        idGenerator = 0L;
        for (int i = 0; i < 20; i++) {
            Customer c = new Customer();
            c.setFirstName("Name " + (i + 1));
            c.setLastName("Last " + (i + 1));
            c.setAddress("Adress " + (i + 1));
            c.setNif("" + (12345678 + i) + ('A' + i));
            saveCustomer(c);
        }
    }
    
    @Lock(LockType.WRITE)
    @Override
    public void saveCustomer(Customer c) {
        if (c.getId() == null) {
            c.setId(++idGenerator);
        }
        System.out.println("Guardant el customer " + c);
        customers.put(c.getId(), c);
        
    }
    
    @Lock(LockType.READ)
    @Override
    public Customer getCustomer(Long id) {
        System.out.println("Obtenint el customer " + id);
        if(!customers.containsKey(id)){
            throw new CustomerNotFoundException();
        }
        return customers.get(id);
    }
    
    @Lock(LockType.WRITE)
    @Override
    public void deleteCustomer(Long id) {
        System.out.println("Eliminant el customer " + id);
        customers.remove(id);
    }
    
    
    @Lock(LockType.READ)
    @Override
    public List<Customer> getCustomers(CustomersSearchCriteria cc) {
        Stream<Customer> cust = customers.values().stream()
                .peek(c -> System.out.println("trying "+c));
        
        if (cc.getFirstNameEquals() != null) {
            cust = cust.filter(c -> {
                    System.out.println("Checking "+cc.getFirstNameEquals() +
                            " == "+c.getFirstName());
                 return cc.getFirstNameEquals().equals(c.getFirstName());
                    });
        }
        if (cc.getLastNameEquals() != null) {
            cust = cust.filter(c -> cc.getLastNameEquals().equals(c.getLastName()));
        }
        if (cc.getNifEquals() != null) {
            cust = cust.filter(c -> cc.getNifEquals().equals(c.getNif()));
        }
        
        return cust
                .peek(c -> System.out.println("passed "+c))
                .limit(cc.getEnd())
                .skip(cc.getStart())
                .collect(Collectors.toList());
    }
    
}
