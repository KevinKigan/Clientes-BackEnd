package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.models.entity.Factura;

public interface FacturaServiceInterface {
    Factura findById(int id);
    Factura save(Factura factura);
    void delete(int id);
}
