/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.customers;

import java.util.List;
import javax.ejb.Local;
import net.sergigabol.orderrestservice.domain.Customer;

/**
 *
 * @author gabalca
 */
@Local
public interface CustomersLocal {
    public void saveCustomer(Customer c);
    public Customer getCustomer(Long id);
    public void deleteCustomer(Long id);
    public List<Customer> getCustomers(int offset, int end);
    public List<Customer> getCustomers(int offset, int end, CustomersSearchCriteria cc);
}
