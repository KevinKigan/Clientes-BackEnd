package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.models.DAO.FacturaDAOInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaService implements FacturaServiceInterface{

    @Autowired
    private FacturaDAOInterface facturaDao;

    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Factura findById(int id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Factura save(Factura factura) {
        return facturaDao.save(factura);
    }

    @Override
    @Transactional
    public void delete(int id) {
        facturaDao.deleteById(id);
    }
}
