package com.kevingomez.springbootbackendapirest.auth;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * Metodo de configuracion de seguridad de las rutas. Se especifica
     * que permisos se necesitan para acceder a dada ruta
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /* Rutas publicas a las que puede acceder cualquiera, el resto tienen que estar autenticados */
                .antMatchers(HttpMethod.GET,"/api/clientes", "/api/clientes/page/**", "/api/uploads/img/**","/Images/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.POST,"/api/clientes/upload").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.POST,"/api/clientes").hasRole("ADMIN")
//                .antMatchers("/api/clientes/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
    }

    /**
     * Metodo para configurar los permisos del CORS
     *
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Metodo para crear y registrar un filtro de CORS con la confifuracion,
     * dandole la prioridad mas alta
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
