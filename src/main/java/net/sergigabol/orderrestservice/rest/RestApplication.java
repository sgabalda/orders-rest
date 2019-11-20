/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Context;
import net.sergigabol.orderrestservice.rest.filters.MethodChangeFilter;

/**
 *
 * @author gabalca
 */
@ApplicationPath("/rest")
public class RestApplication extends Application{

    /*
    public RestApplication(@Context Configurable configurable){
        //si volem configurar el filtre programaticament
        //configurable.register(MethodChangeFilter.class,Priorities.HEADER_DECORATOR);
    }
*/
    
}
