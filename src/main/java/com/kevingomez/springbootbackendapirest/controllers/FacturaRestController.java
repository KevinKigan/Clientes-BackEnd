package com.kevingomez.springbootbackendapirest.controllers;

import com.kevingomez.springbootbackendapirest.models.DAO.Services.FacturaServiceInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Factura;
import com.kevingomez.springbootbackendapirest.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class FacturaRestController {
    @Autowired
    private FacturaServiceInterface facturaService;

    /**
     * Metodo para mostrar la factura
     *
     * @param id
     * @return
     */
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/facturas/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Factura show(@PathVariable int id){
        return facturaService.findById(id);
    }

    /**
     * metodo para borrar la factura
     *
     * @param id
     */
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/facturas/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        facturaService.delete(id);
    }

    /**
     * Metodo para filtrar los productos segun la busqueda
     *
     * @param term
     * @return
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/facturas/filtrar-productos/{term}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Producto> filterProducts(@PathVariable String term){
        return facturaService.findByProductNameContainingIgnoreCase(term );
    }

    /**
     * Metodo para crear la factura
     *
     * @param factura
     * @return
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/facturas")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Factura create(@RequestBody Factura factura){
        return facturaService.save(factura);
    }

}
