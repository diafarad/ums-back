package com.api.docsen.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558953243875L;
    // Ceci est appelé lorsque l'utilisateur tente d'accéder à une ressource REST sécurisée
    // sans fournir d'informations d'identification
    // Nous devrions simplement envoyer une réponse 401 non autorisée car il n'y a pas
    // de "page de connexion" vers laquelle rediriger
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
