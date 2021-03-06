package com.kevingomez.springbootbackendapirest.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 20)
    private String username;
    @Column(length = 60)
    private String password;
    private Boolean enabled;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String mail;

    /*
        Si quisieramos cambiar el nombre de la tabla o el de los campos, seria:
        @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinTable(name = "user_auhorities", joinColumns=@JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Si se elimina al usuario, se eliminan sus roles e igual para la creacion
    //@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol_id"})})
    @JoinTable(name = "usuarios_roles", joinColumns=@JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private List<Rol> roles;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
