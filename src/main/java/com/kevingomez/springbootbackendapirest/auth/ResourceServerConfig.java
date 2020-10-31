package com.kevingomez.springbootbackendapirest.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /* Rutas publicas a las que puede acceder cualquiera, el resto tienen que estar autenticados */
                .antMatchers(HttpMethod.GET,"/api/clientes")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}