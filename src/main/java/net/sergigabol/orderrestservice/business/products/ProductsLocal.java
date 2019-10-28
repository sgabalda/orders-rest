/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.business.products;

import java.util.List;
import javax.ejb.Local;
import net.sergigabol.orderrestservice.domain.Order;
import net.sergigabol.orderrestservice.domain.Product;

/**
 *
 * @author gabalca
 */
@Local
public interface ProductsLocal {
    
    void saveProduct(Product p);
    void deleteProduct(Long productId);
    Product getProductById(Long productId);
    List<Product> getAllProducts(int offset, int end);
}
