package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.controllers.ClienteRestController;
import com.kevingomez.springbootbackendapirest.models.DAO.UsuarioDAOInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Usuario;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioService implements UserDetailsService, UsuarioServiceInterface {
    private static Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioDAOInterface usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioDao.findByUsername(username);
        if(user==null){
            log.error("Error en el login, no existe el usuario: "+username);
            throw new UsernameNotFoundException("Error en el login, no existe el usuario: "+username);
        }
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(rol ->new SimpleGrantedAuthority(rol.getRolName()))
                .peek(authority -> log.info("Rol: "+authority.getAuthority()))
                .collect(Collectors.toList());
        return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true,true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    /* El transactional maneja los estados y las posibles modificacioens de los objetos cuando se hace
    *  las consultas a db, si anotamos con readOnly, no se guardan los estados de los objetos,
    *  por lo que no puede detectar que se han modificado y por lo tanto no los guarda en la db
    */
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }
}
