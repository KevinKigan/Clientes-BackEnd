package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.models.DAO.ClienteDAOInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import com.kevingomez.springbootbackendapirest.models.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Cliente findById(int id) {
        return clienteDAO.findById(id).orElse(null); // Lo retorna si lo encuentra y en caso contrario retorna null
    }

    @Override
    @Transactional // Permite tanto leer como escribir
    public Cliente save(Cliente cliente) {
        return clienteDAO.save(cliente);
    }

    @Override
    @Transactional
    public void delete(int id) {
        clienteDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegions() {
        return clienteDAO.findAllRegions();
    }
}
