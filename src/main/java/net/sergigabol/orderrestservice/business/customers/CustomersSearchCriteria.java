/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.customers;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

/**
 *
 * si es busca per ?nif={nif}&first={first}&last={last} que es mapegi
 * automaticament
 *
 * @author gabalca
 */
public class CustomersSearchCriteria {

    @QueryParam("first")
    private String firstNameEquals;
    @QueryParam("last")
    private String lastNameEquals;
    @QueryParam("nif")
    private String nifEquals;
    
    @DefaultValue("0")
    @QueryParam("start")
    private int start = 0;

    @DefaultValue("10")
    @QueryParam("end")
    private int end = 10;

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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "CustomersSearchCriteria{" + "firstNameEquals=" + firstNameEquals + ", lastNameEquals=" + lastNameEquals + ", nifEquals=" + nifEquals + ", start=" + start + ", end=" + end + '}';
    }
    
    

}
