package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.models.entity.Cliente;

import java.util.List;

public interface ClienteServiceInterface {
    public List<Cliente> findAll();
}
