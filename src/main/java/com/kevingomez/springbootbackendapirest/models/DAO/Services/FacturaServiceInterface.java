package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.models.entity.Factura;
import com.kevingomez.springbootbackendapirest.models.entity.Producto;

import java.util.List;

public interface FacturaServiceInterface {
    Factura findById(int id);
    Factura save(Factura factura);
    void delete(int id);
    List<Producto> findByProductNameContainingIgnoreCase(String term);
}
