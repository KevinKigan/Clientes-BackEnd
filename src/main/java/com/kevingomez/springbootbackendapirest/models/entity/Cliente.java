package com.kevingomez.springbootbackendapirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Clientes") // Esto no es necesario si la clase se llama igual que la tabla
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El valor del id se genera de manera autoincremental
    private int id;
    // Las tres siguientes variables son columnas pero si se llaman igual que en la bbdd no hace falta poner @Column

    @NotEmpty(message = "El campo no puede estar vacio")
    @Size(min = 3, max = 20)
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @NotEmpty(message = "El campo no puede estar vacio")
    @Size(min = 3, max = 20)
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "El campo no puede estar vacio")
    @Email(message = "Tiene que ser una direccion de correo bien formada")
    @Column(nullable = false, unique = false)
    private String email;

    @NotNull
    @Temporal(TemporalType.DATE) // Temporal indica cual va a ser el tipo equivalente en la bbdd
    @Column(name = "create_at")
    private Date createAt;

    private String photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @NotNull(message = "La region no puede estar vacia")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Region region;



    public Region getRegion() { return region; }

    public void setRegion(Region region) { this.region = region; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getclientName() {
        return clientName;
    }

    public void setclientName(String clientName) {
        this.clientName = clientName;
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

    public String getPhoto() { return photo; }

    public void setPhoto(String photo) { this.photo = photo; }

    private static final long serialVersionUID = 1L;
}
