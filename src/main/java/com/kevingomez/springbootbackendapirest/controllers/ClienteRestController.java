package com.kevingomez.springbootbackendapirest.controllers;

import com.kevingomez.springbootbackendapirest.models.DAO.Services.ClienteServiceInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    private static final int ELEMENTSFORPAGE = 4;
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
     * Metodo para retornar todos los clientes
     *
     * @return Lista de clientes
     */
    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page){  //Peticion de tipo get
        return clienteService.findAll(PageRequest.of(page, ELEMENTSFORPAGE));
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
            response.put("error", e.getMostSpecificCause().getMessage());
            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
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
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult bindingResult){
        Cliente clienteNew;
        Map<String, Object> response = new HashMap<>();

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            clienteNew = clienteService.save(cliente);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            String msg = e.getMostSpecificCause().getMessage();
            if(msg.contains("Duplicate entry")){
                response.put("error", msg.split(" for key")[0]);
            }else{
                response.put("error", msg);
            }

            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
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
     * @return ResponseEntity. Nos Permite pasar un mensaje de error y nuestro objeto entity a la respuesta
     */
    @PutMapping("/clientes/{id}")  // Se utiliza para actualizar un cliente
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult bindingResult, @PathVariable int id){
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdated;
        Map<String, Object> response = new HashMap<>();
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
        }

        if(clienteActual == null){
            response.put("mensaje", "Error: no se puede editar, el cliente ID: ".concat(Integer.toString(id).concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try{
            clienteActual.setclientName (cliente.getclientName());
            clienteActual.setEmail (cliente.getEmail());
            clienteActual.setLastName (cliente.getLastName());
            clienteUpdated = clienteService.save(clienteActual);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            String msg = e.getMostSpecificCause().getMessage();
            if(msg.contains("Duplicate entry")){
                response.put("error", msg.split(" for key")[0]);
            }else{
                response.put("error", msg);
            }
            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido actualizado con exito");
        response.put("cliente", clienteUpdated);
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }


    /**
     * Metodo para borar un cliente de la BBDD
     *
     * @param id ID del cliente a borrar
     * @return ResponseEntity. Nos Permite pasar un mensaje de error y nuestro objeto entity a la respuesta
     */
    @DeleteMapping("/clientes/{id}")

    public ResponseEntity<?> delete(@PathVariable int id) {


        Map<String, Object> response = new HashMap<>();
        try{
            clienteService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el cliente de la base de datos");
            response.put("error", e.getMostSpecificCause().getMessage());
            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido eliminado con exito");
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
