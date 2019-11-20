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
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Variant;
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

    @Context
    HttpHeaders headers;
    
    @Context
    Request request;
    
    @Override
    public Response getProduct(long productId) {
        System.out.println("Getting product with id "+productId);
        Product p = productsBean.getProductById(productId);
        
        Variant v1 = new Variant(MediaType.APPLICATION_XML_TYPE, Locale.ENGLISH, "gzip");
        Variant v2 = new Variant(MediaType.APPLICATION_JSON_TYPE, new Locale("es"), "deflate");
        
        List<Variant> variants = Arrays.asList(v1,v2);
        
        Variant respVariant = request.selectVariant(variants);
        
        System.out.println("En el JSON o XML");
        if(p!=null){
            
            Link delete = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                    .rel("delete")
                    .type("DELETE")
                    .build();
            Link update = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                    .rel("update")
                    .type("PUT")
                    .build();
            
            p.setLink(Arrays.asList(delete,update));
            
            CacheControl cc = new CacheControl();
            cc.setMaxAge(3600);
            cc.setPrivate(false);
            cc.setNoTransform(true);
            
            return Response.ok(p)
                    .links(delete,update)
                    .type(respVariant.getMediaType())
                    .language(respVariant.getLanguage())
                    .cacheControl(cc)
                    //.encoding(respVariant.getEncoding())
                    .expires(new Date(ZonedDateTime.now().plusHours(1).toInstant().toEpochMilli()))
                    .build();
        }else{
            throw new NotFoundException();
        }
    }

    @Override
    public Response getProductAsText(long productId) {
        System.out.println("En el text");
        Product p = productsBean.getProductById(productId);
        if(p!=null){
            
            CacheControl cc = new CacheControl();
            cc.setMaxAge(3600);
            cc.setPrivate(false);
            cc.setNoTransform(true);
            
            return Response
                    .ok(p.toString())
                    .cacheControl(cc)
                    .expires(new Date(ZonedDateTime.now().plusHours(1).toInstant().toEpochMilli()))
                    .build();
        }else{
            throw new NotFoundException();
        }
    }
    
}
