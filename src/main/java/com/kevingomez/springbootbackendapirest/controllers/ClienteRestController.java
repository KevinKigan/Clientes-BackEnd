package com.kevingomez.springbootbackendapirest.controllers;

import com.kevingomez.springbootbackendapirest.models.DAO.Services.ClienteServiceInterface;
import com.kevingomez.springbootbackendapirest.models.DAO.Services.UploadFileServiceInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import com.kevingomez.springbootbackendapirest.models.entity.Region;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
@Slf4j
public class ClienteRestController {

    private static final int ELEMENTSFORPAGE = 4;
    private static Logger log = LoggerFactory.getLogger(ClienteRestController.class);

    @Autowired
    private ClienteServiceInterface clienteService;

    @Autowired
    private UploadFileServiceInterface uploadFileService;

    /**
     * Metodo para retornar todos los clientes
     *
     * @return Lista de clientes
     */
    @GetMapping("/clientes")
    public List<Cliente> index() {  //Peticion de tipo get
        return clienteService.findAll();
    }

    /**
     * Metodo para retornar todos los clientes
     *
     * @return Lista de clientes
     */
    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page) {  //Peticion de tipo get
        return clienteService.findAll(PageRequest.of(page, ELEMENTSFORPAGE));
    }


    /**
     * Metodo para retornar un cliente en especifico por id
     *
     * @param id ID del cliente a buscar
     * @return ResponseEntity. Nos Permite pasar un mensaje de error y nuestro objeto entity a la respuesta
     */

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable int id) {
        Cliente cliente;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMostSpecificCause().getMessage());
            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
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

    @Secured("ROLE_ADMIN")
    @PostMapping("/clientes") // Se utiliza para crear un nuevo cliente
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult bindingResult) {
        Cliente clienteNew;
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clienteNew = clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            String msg = e.getMostSpecificCause().getMessage();
            if (msg.contains("Duplicate entry")) {
                response.put("error", msg.split(" for key")[0]);
            } else {
                response.put("error", msg);
            }

            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido creado con exito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    } // RequestBody porque viene en formato json

    /**
     * Metodo para actualizar un cliente
     *
     * @param id ID del cliente a actualizar
     * @return ResponseEntity. Nos Permite pasar un mensaje de error y nuestro objeto entity a la respuesta
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/clientes/{id}")  // Se utiliza para actualizar un cliente
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult bindingResult, @PathVariable int id) {
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdated;
        Map<String, Object> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
        }

        if (clienteActual == null) {
            response.put("mensaje", "Error: no se puede editar, el cliente ID: ".concat(Integer.toString(id).concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteActual.setclientName(cliente.getclientName());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setLastName(cliente.getLastName());
            clienteActual.setRegion(cliente.getRegion());
            clienteUpdated = clienteService.save(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            String msg = e.getMostSpecificCause().getMessage();
            if (msg.contains("Duplicate entry")) {
                response.put("error", msg.split(" for key")[0]);
            } else {
                response.put("error", msg);
            }
            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido actualizado con exito");
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
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable int id) {


        Map<String, Object> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.findById(id);
            findAndDeletePhoto(cliente);
            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente de la base de datos");
            response.put("error", e.getMostSpecificCause().getMessage());
            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido eliminado con exito");
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    /**
     * Metodo para subir una foto del cliente
     *
     * @param file
     * @param id
     * @return
     */

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping("/clientes/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") int id) {
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = clienteService.findById(id);
        try {
            if (!file.isEmpty()) {
                String fileName = uploadFileService.copy(file);
                findAndDeletePhoto(cliente);
                cliente.setPhoto(fileName);
                clienteService.save(cliente);
                response.put("cliente", cliente);
                response.put("mensaje", "Se ha subido correctamente la imagen: " + fileName);
            }
        } catch (IOException e) {
            response.put("error", e.getCause().getMessage());
            response.put("error Especifico", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }

    @GetMapping("uploads/img/{namePhoto:.+}")
    // :.+ Indica que la foto tiene un nombre y una extension que puede ser cualquiera
    public ResponseEntity<Resource> showPhoto(@PathVariable String namePhoto) {
        Resource resource = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            resource = uploadFileService.load(namePhoto);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }


    /**
     * Metodo para buscar si el cliente tiene foto y borrarla si la posee
     *
     * @param cliente
     */
    public void findAndDeletePhoto(Cliente cliente) {
        String lastFileName = cliente.getPhoto();
        uploadFileService.delete(lastFileName);
    }

    /**
     * Metodo para retornar todos las regiones
     *
     * @return Lista de regiones
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/clientes/regiones")
    public List<Region> listRegions() {  //Peticion de tipo get
        return clienteService.findAllRegions();
    }
}
