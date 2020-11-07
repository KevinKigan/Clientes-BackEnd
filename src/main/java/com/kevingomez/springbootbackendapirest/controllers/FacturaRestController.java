package com.kevingomez.springbootbackendapirest.controllers;

import com.kevingomez.springbootbackendapirest.models.DAO.Services.ClienteServiceInterface;
import com.kevingomez.springbootbackendapirest.models.DAO.Services.FacturaServiceInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class FacturaRestController {
    @Autowired
    private FacturaServiceInterface facturaService;

    @GetMapping("/facturas/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Factura show(@PathVariable int id){
        return facturaService.findById(id);
    }
}
