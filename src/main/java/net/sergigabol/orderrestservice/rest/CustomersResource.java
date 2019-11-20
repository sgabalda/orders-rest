/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.sergigabol.orderrestservice.business.customers.CustomersLocal;
import net.sergigabol.orderrestservice.business.customers.CustomersSearchCriteria;
import net.sergigabol.orderrestservice.domain.Customer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author gabalca
 */
@RequestScoped
public class CustomersResource implements CustomersResourceInterface {

    private int defaultPaginationSize = 10;

    @EJB
    private CustomersLocal customersBean;

    @Context
    public void getInitParams(ServletContext ctx) {
        defaultPaginationSize = Integer.parseInt(
                ctx.getInitParameter("default-pagination-size"));
    }

    public Response createCustomer(InputStream is) {

        Customer c = readCustomer(is);

        customersBean.saveCustomer(c);
        

        return Response.created(URI.create("/customers/" + c.getId())).build();

    }

    public StreamingOutput getAllCustomers(CustomersSearchCriteria csc) {

        System.out.println("Criteria is "+csc);
        
        List<Customer> customers = customersBean.getCustomers(csc);

        return new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException, WebApplicationException {
                try (PrintWriter pw = new PrintWriter(out)) {
                    pw.println("<customers>");
                    for (Customer c : customers) {
                        writeCustomer(c, pw);
                    }
                    pw.println("</customers>");
                }
            }
        };

    }

    public StreamingOutput getCustomer(@PathParam("id") long custId) {
        Customer customer = customersBean.getCustomer(custId);

        //return Response.ok(customer).build();
        return new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException, WebApplicationException {
                try (PrintWriter pw = new PrintWriter(out)) {
                    writeCustomer(customer, pw);
                }
            }
        };

    }

    public Response deleteCustomer(@PathParam("id") long custId) {
        customersBean.deleteCustomer(custId);
        return Response.noContent().build();
    }
    
    @Context
    SecurityContext securityCtx;

    @Override
    public Response updateCustomer(InputStream is, @PathParam("id") long id) {
        
        Principal p =securityCtx.getUserPrincipal();
        
        Customer c = readCustomer(is);
        Customer current = customersBean.getCustomer(id);
        //TODO canviar això: si no el troba potser millor que llenci excepció
        if (current == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        c.setId(id);
        customersBean.saveCustomer(c);
        return Response.noContent().build();
    }

    protected void writeCustomer(Customer cust, PrintWriter pw) throws IOException {
        pw.println("<customer id='" + cust.getId() + "'>");
        pw.println(" <first-name>" + cust.getFirstName() + "</first-name>");
        pw.println(" <last-name>" + cust.getLastName() + "</last-name>");
        pw.println(" <address>" + cust.getAddress() + "</address>");
        pw.println("</customer>");
    }

    protected Customer readCustomer(InputStream is) {

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();

            Customer customer = new Customer();
            if (root.getAttribute("id") != null
                    && !root.getAttribute("id").trim().isEmpty()) {
                customer.setId(Long.parseLong(root.getAttribute("id")));
            }
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    //String name = n.getNodeName();
                    Element e = (Element) nodes.item(i);

                    if (e.getTagName().equals("first-name")) {
                        customer.setFirstName(e.getTextContent());
                    } else if (e.getTagName().equals("last-name")) {
                        customer.setLastName(e.getTextContent());
                    } else if (e.getTagName().equals("address")) {
                        customer.setAddress(e.getTextContent());
                    }
                }
            }
            return customer;
        } catch (NumberFormatException | ParserConfigurationException
                | SAXException | IOException ex) {
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        }
    }

    /*
    <customer id="xx">
        <first-name>Juan</first-name>
        <last-name>Juan</last-name>
        <address>Juan</address>
    </customer>
     */

    @Override
    public StreamingOutput getCustomerByFullName(String first, String last) {
        CustomersSearchCriteria cc = new CustomersSearchCriteria();
        cc.setFirstNameEquals(first);
        cc.setLastNameEquals(first);
        List<Customer> result
                = customersBean.getCustomers(cc);
        if (result.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return new StreamingOutput() {
                @Override
                public void write(OutputStream out) throws IOException, WebApplicationException {
                    try (PrintWriter pw = new PrintWriter(out)) {
                        writeCustomer(result.get(0), pw);
                    }
                }
            };
        }
    }

    @Override
    public StreamingOutput getCustomerByNif(String nif) {
        CustomersSearchCriteria cc = new CustomersSearchCriteria();
        cc.setNifEquals(nif);
        List<Customer> result
                = customersBean.getCustomers(cc);
        if (result.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return new StreamingOutput() {
                @Override
                public void write(OutputStream out) throws IOException, WebApplicationException {
                    try (PrintWriter pw = new PrintWriter(out)) {
                        writeCustomer(result.get(0), pw);
                    }
                }
            };
        }
    }
    
    @Inject
    private Instance<OrdersResourceImpl> orderResInstance;

    @Override
    public Object getOrdersSubresource(long customerId) {
        
        OrdersResourceImpl result = orderResInstance.get();
        
        result.setCustomerId(customerId);
        return result;
    }

}
