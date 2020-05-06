package com.kevingomez.springbootbackendapirest.controllers;

import com.kevingomez.springbootbackendapirest.models.DAO.Services.ClienteServiceInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private ClienteServiceInterface clienteService;

    @GetMapping("/clientes")  // Peticion de tipo get
    public List<Cliente> index(){
        return clienteService.findAll();
    }
}
