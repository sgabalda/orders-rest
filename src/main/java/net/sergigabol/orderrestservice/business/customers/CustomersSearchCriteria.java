/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.customers;

/**
 *
 * @author gabalca
 */
public class CustomersSearchCriteria {
    
    private String firstNameEquals;
    private String lastNameEquals;
    private String nifEquals;

    public String getFirstNameEquals() {
        return firstNameEquals;
    }

    public void setFirstNameEquals(String firstNameEquals) {
        this.firstNameEquals = firstNameEquals;
    }

    public String getLastNameEquals() {
        return lastNameEquals;
    }

    public void setLastNameEquals(String lastNameEquals) {
        this.lastNameEquals = lastNameEquals;
    }

    public String getNifEquals() {
        return nifEquals;
    }

    public void setNifEquals(String nifEquals) {
        this.nifEquals = nifEquals;
    }
    
    
    
}
