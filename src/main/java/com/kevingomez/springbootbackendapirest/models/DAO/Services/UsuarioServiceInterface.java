package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.models.entity.Usuario;

public interface UsuarioServiceInterface {
    Usuario findByUsername(String username);
}
