package com.kevingomez.springbootbackendapirest.controllers;

import com.kevingomez.springbootbackendapirest.models.DAO.Services.ClienteServiceInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private ClienteServiceInterface clienteService;

    /**
     * Metodo para retornar todos los clientes
     *
     * @return Lista de clientes
     */
    @GetMapping("/clientes")
    public List<Cliente> index(){  //Peticion de tipo get
        return clienteService.findAll();
    }

    /**
     * Metodo para retornar un cliente en especifico por id
     *
     * @param id ID del cliente a buscar
     * @return ResponseEntity. Nos Permite pasar un mensaje de error y nuestro objeto entity a la respuesta
     */

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable int id){
        Cliente cliente;
        Map<String, Object> response = new HashMap<>();
        try{
            cliente =clienteService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente == null){
            response.put("mensaje", "El cliente ID: ".concat(Integer.toString(id).concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Metodo para crear un nuevo cliente
     *
     * @param cliente Cliente a crear
     * @return ResponseEntity. Nos Permite pasar un mensaje de error y nuestro objeto entity a la respuesta
     */

    @PostMapping("/clientes") // Se utiliza para crear un nuevo cliente
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        Cliente clienteNew;
        Map<String, Object> response = new HashMap<>();
        try{
            clienteNew = clienteService.save(cliente);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido creado con exito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    } // RequestBody porque viene en formato json

    /**
     * Metodo para actualizar un cliente
     *
     * @param id ID del cliente a actualizar
     * @return Cliente actualizado
     */
    @PutMapping("/clientes/{id}")  // Se utiliza para actualizar un cliente
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente update(@RequestBody Cliente cliente, @PathVariable int id){
        Cliente clienteActual = clienteService.findById(id);

        clienteActual.setclientName (cliente.getclientName());
        clienteActual.setEmail (cliente.getEmail());
        clienteActual.setLastName (cliente.getLastName());

        return clienteService.save(clienteActual);
    }

    /**
     * Metodo para borar un cliente de la BBDD
     *
     * @param id ID del cliente a borrar
     */
    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        clienteService.delete(id);
    }
}
