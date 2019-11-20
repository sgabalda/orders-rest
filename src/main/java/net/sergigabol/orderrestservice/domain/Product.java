/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.domain;

import java.math.BigDecimal;
import java.util.List;
import javax.json.bind.annotation.JsonbAnnotation;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import net.sergigabol.orderrestservice.rest.JsonbLinksAdapter;

/**
 *
 * @author gabalca
 */
@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
@JsonbNillable
//@JsonbTypeAdapter(ProductAdapter.class)
public class Product {
    
    @XmlAttribute
    private Long id;
    
    @JsonbProperty(nillable = true)
    private String name;
    
    @JsonbProperty("preu")
    private BigDecimal cost;
    
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    @JsonbTypeAdapter(JsonbLinksAdapter.class)
    private List<Link> link;

    public List<Link> getLink() {
        return link;
    }

    public void setLink(List<Link> links) {
        this.link = links;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", cost=" + cost + '}';
    }
    
    
}
