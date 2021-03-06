package com.kevingomez.springbootbackendapirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdditionalInfoToken additionalInfoToken;

    /**
     * Metodo para manejar la seguridad de cada llamada al servicio REST
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // Permite a todos acceder a la ruta de login /oauth/token/
        security.tokenKeyAccess("permitAll()")
                // Verifica que el token sea correcto /oauth/check_token
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * Configuracion para el acceso de la aplicacion
     *
     * @param clients
     * @throws Exception
     */

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Registramos la aplicacion con su contraseña para codificar
        clients.inMemory().withClient("angularapp")
                .secret(passwordEncoder.encode("Angular&Spring5"))
                /* Se podrá leer y escribir */
                .scopes("read","write")
                /* La autorizacion de los usuarios será mediante contraseña como un login normal y
                    el token se actualizará cada 4 horas. Se tendrá que volver a hacer login */
                .authorizedGrantTypes("password","refresh_token")
                .accessTokenValiditySeconds(14400)
                .refreshTokenValiditySeconds(14400);
    }

    /**
     * Metodo ara añadir informacin cuando se realiza la consulta mediante
     * el token y permitir el acceso usando el token
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(additionalInfoToken, accessTokenConverter()));
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * Metodo para la verificacion del token mediante clave publica y privada
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(JwtConfig.PRIVATE_KEY);
        jwtAccessTokenConverter.setVerifierKey(JwtConfig.PUBLIC_KEY);
        return jwtAccessTokenConverter;
    }
}
