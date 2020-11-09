package com.kevingomez.springbootbackendapirest.auth;

import com.kevingomez.springbootbackendapirest.models.DAO.Services.UsuarioServiceInterface;
import com.kevingomez.springbootbackendapirest.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdditionalInfoToken implements TokenEnhancer {

    @Autowired
    private UsuarioServiceInterface usuarioService;

    /**
     * Metodo para añadir informacion sobre el cliente
     *
     * @param oAuth2AccessToken
     * @param oAuth2Authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Usuario user = usuarioService.findByUsername(oAuth2Authentication.getName());
        Map<String, Object> info = new HashMap<>();
        info.put("info_adicional","hi ".concat(oAuth2Authentication.getName()));
        info.put("firstName", user.getFirstName());
        info.put("LastName", user.getLastName());
        info.put("Mail", user.getMail());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
