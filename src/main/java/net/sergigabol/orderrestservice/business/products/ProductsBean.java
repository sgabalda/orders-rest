/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.products;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import net.sergigabol.orderrestservice.domain.Product;

/**
 *
 * @author gabalca
 */
@Singleton
public class ProductsBean implements ProductsLocal {

    private Map<Long, Product> products;
    private long idGenerator;

    @PostConstruct
    public void init() {
        products = new HashMap<>();
        idGenerator = 0L;
        
        for(int i = 0; i<100; i++){
            Product p = new Product();
            p.setName("Producte "+(i+1));
            p.setCost(new BigDecimal(i+1));
            saveProduct(p);
        }
        
    }

    @Lock(LockType.WRITE)
    @Override
    public void saveProduct(Product o) {
        if (o.getId() == null) {
            o.setId(++idGenerator);
        }
        products.put(o.getId(), o);
    }

    @Lock(LockType.WRITE)
    @Override
    public void deleteProduct(Long productId) {
        products.remove(productId);

    }

    @Lock(LockType.READ)
    @Override
    public Product getProductById(Long productId) {
        return products.get(productId);
    }

    @Lock(LockType.READ)
    @Override
    public List<Product> getAllProducts(int offset, int end, 
            List<String> orderby) {
        Comparator<Product> comp = null;
        if(orderby!=null && !orderby.isEmpty()){
            for(String order:orderby){
                switch(order){
                    case "id": 
                        comp = (comp==null)?
                            Comparator.comparing(Product::getId):
                            comp.thenComparing(Comparator.comparing(Product::getId));
                        break;
                    case "nom": 
                        comp = (comp==null)?
                            Comparator.comparing(Product::getName):
                            comp.thenComparing(Comparator.comparing(Product::getName));
                        break;
                    case "cost": 
                        comp = (comp==null)?
                            Comparator.comparing(Product::getCost):
                            comp.thenComparing(Comparator.comparing(Product::getCost));
                        break;
                }
                        
            }
        }
        Stream<Product> productsSt = products.values().stream();
        if(comp!=null) productsSt = productsSt.sorted(comp);
        return productsSt
                .limit(end)
                .skip(offset)
                .collect(Collectors.toList());
    }

}
