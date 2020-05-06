package com.kevingomez.springbootbackendapirest.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Clientes") // Esto no es necesario si la clase se llama igual que la tabla
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El valor del id se genera de manera autoincremental
    private int id;
    // Las tres siguientes variables son columnas pero si se llaman igual que en la bbdd no hace falta poner @Column
    @Column(name = "name_person")
    private String namePerson;
    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE) // Temporal indica cual va a ser el tipo equivalente en la bbdd
    private Date createAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getnamePerson() {
        return namePerson;
    }

    public void setnamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}
