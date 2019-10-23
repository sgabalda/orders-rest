/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URI;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.sergigabol.orderrestservice.business.CustomersLocal;
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
@Path("/customers")
public class CustomersResource {

    @EJB
    private CustomersLocal customersBean;

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createCustomer(InputStream is) {

        Customer c = readCustomer(is);

        customersBean.saveCustomer(c);

        return Response.created(URI.create("/customers/" + c.getId())).build();

    }
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StreamingOutput getCustomer(@PathParam("id") long custId){
        Customer customer = customersBean.getCustomer(custId);

        //return Response.ok(customer).build();
        
        return new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException, WebApplicationException {
                writeCustomer(customer, out);
            }
        };
        
    }
    
    protected void writeCustomer(Customer cust, OutputStream out) throws IOException{
        PrintWriter pw = new PrintWriter(out);
        pw.println("<customer id='"+cust.getId()+"'>");
        pw.println(" <first-name>"+cust.getFirstName()+"</first-name>");
        pw.println(" <last-name>"+cust.getLastName()+"</last-name>");
        pw.println(" <address>"+cust.getAddress()+"</address>");
        pw.println("</customer>");
        pw.close();
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

}
