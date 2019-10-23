/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.ejb.Stateless;
import net.sergigabol.orderrestservice.domain.Customer;

/**
 *
 * @author gabalca
 */
@Stateless
public class CustomersBean implements CustomersLocal{

    @Override
    public void saveCustomer(Customer c) {

        if(c.getId()==null){
            c.setId(22L);
        }
        System.out.println("Guardant el customer "+c.getId());
        
    }

    @Override
    public Customer getCustomer(Long id) {
        System.out.println("Obtenint el customer "+id);
        Customer c = new Customer();
        
        c.setFirstName("Joan");
        c.setLastName("Perez");
        c.setId(id);
        
        return c;
    }

    @Override
    public void deleteCustomer(Long id) {
        System.out.println("Eliminant el customer "+id);
    }

    @Override
    public List<Customer> getCustomers(int offset, int end) {
        return new ArrayList<>();
    }
    
}
