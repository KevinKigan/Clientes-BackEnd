package com.kevingomez.springbootbackendapirest.models.DAO;

import com.kevingomez.springbootbackendapirest.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDAOInterface extends CrudRepository<Usuario, Integer> {
    Usuario findByUsername(String username);

    /*  Otra manera de escribirlo:
        @Query("select u from Usuario u where u.username=?1")
        Usuario findByUsername2(String username);
    */

}
