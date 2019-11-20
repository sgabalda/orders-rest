/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest.filters;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 * Filtre que implementa la sobreescritura del security context amb una capcalera 
 * Authorization: username,rol1,rol2,rol3
 * @author gabalca
 */
@Provider
public class MyCustomAuthFilter implements ContainerRequestFilter{

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        String authHeader = crc.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(authHeader==null) return;
        
        String [] tokens = authHeader.split(",");
        String username = tokens[0];
        List<String> roles = Arrays.asList(Arrays.copyOfRange(tokens, 1, tokens.length));
 
        crc.setSecurityContext(new SecurityContext(){
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return username;
                    }
                };
            }

            @Override
            public boolean isUserInRole(String string) {
                return roles.contains(string);
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return "CUSTOM";
            }
            
        });
    }
    
}
