package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.models.DAO.ClienteDAOInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService implements ClienteServiceInterface {

    @Autowired  // Crea una instacia de una implementacion y queda guardada en el contexto de spring
    private ClienteDAOInterface clienteDAO;

    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDAO.findAll();
    }
}
