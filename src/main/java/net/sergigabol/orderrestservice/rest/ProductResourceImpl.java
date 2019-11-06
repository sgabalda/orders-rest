/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.sergigabol.orderrestservice.business.products.ProductsLocal;
import net.sergigabol.orderrestservice.domain.Product;

/**
 *
 * @author gabalca
 */
@RequestScoped
public class ProductResourceImpl implements ProductResource{

    @EJB
    ProductsLocal productsBean;
    
    @QueryParam("keywords")
    String keywords;
    
    @Context
    UriInfo uriInfo;
    
    @Override
    public List<Product> getAllProducts(int start, int end, List<String> orderby) {
        System.out.println("Cercant amb les keywords "+keywords);
        return productsBean.getAllProducts(start, end,orderby);
        
    }

    @Override
    public void saveProductImage(InputStream is, long productId) {
        //TODO comprovar que existeix el producte amb el id especificat, si no 404
        //TODO llegir amb un header el content-type i posar l'extensió del fitxer correpsonent
        try {
            Files.copy(is, Paths.get("/home/gabalca/prova/rest/products/"+productId+".png"));
        } catch (IOException ex) {
            Logger.getLogger(ProductResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public File saveProductImage(long productId) {
        //TODO comprovar que el fitxer existeix, si no 404
        return new File("/home/gabalca/prova/rest/products/"+productId+".png");
    }

    @Override
    public void createNewProduct(Product p) {
        productsBean.saveProduct(p);
    }

    @Override
    public Product getProduct(long productId) {
        return productsBean.getProductById(productId);
    }
    
}
