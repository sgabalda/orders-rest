/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.products;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import net.sergigabol.orderrestservice.domain.Order;
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
    public List<Product> getAllProducts(int offset, int end) {
        return products.values().stream()
                .limit(end)
                .skip(offset)
                .collect(Collectors.toList());
    }

}
