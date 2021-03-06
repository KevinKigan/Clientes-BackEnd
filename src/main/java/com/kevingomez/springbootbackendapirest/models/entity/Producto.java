package com.kevingomez.springbootbackendapirest.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    private Double price;

    @Column(name = "create_at")
    private Date createAt;

    @PrePersist
    public void prePersist() throws ParseException {
//        Date objDate = new Date();
//        String strDateFormat = "aaaa-MM-dd";
//        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objeto
//        System.out.println("Fecha -> "+objSDF.parse(objSDF.format(objDate)));
//        this.createAt = objSDF.parse(objSDF.format(objDate));

    }

    public Producto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
